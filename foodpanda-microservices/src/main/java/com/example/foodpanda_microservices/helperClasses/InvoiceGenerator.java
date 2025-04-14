package com.example.foodpanda_microservices.helperClasses;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.extend.FontResolver;
import org.xhtmlrenderer.layout.SharedContext;
import org.xhtmlrenderer.pdf.ITextRenderer;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@Component
public class InvoiceGenerator {


        @Autowired
        private  TemplateEngine templateEngine;

        public  byte[] generateInvoiceV1(List<Map<String,Object>> customerOrderDetails , Map<String,Object> customerDetails) {
            double subTotal= 0.0;
            double total;
            double tax;

            try{
                for(Map<String,Object> map : customerOrderDetails){
                    subTotal+= (double)map.get("price");
                }
                tax = ((double)10 /100 * (subTotal));
                total = subTotal + tax;

                Map<String, Object> completeDetails = new HashMap<>(customerDetails);
                completeDetails.put("orderItems",customerOrderDetails);
                completeDetails.put("subTotal",subTotal);
                completeDetails.put("total",total);
                completeDetails.put("tax",tax);

                Context context = new Context();
                ByteArrayOutputStream outputStream;

                context.setVariables(completeDetails);

                String htmlContent = templateEngine.process("invoice_template", context);
                outputStream = new ByteArrayOutputStream();
                ITextRenderer renderer = new ITextRenderer();
                FontResolver resolver = renderer.getFontResolver();
                renderer.setDocumentFromString(htmlToXhtml(htmlContent));
                renderer.layout();
                SharedContext sContext = renderer.getSharedContext();
                sContext.setDotsPerPixel(12);
                renderer.createPDF(outputStream, false);
                renderer.finishPDF();
                return outputStream.toByteArray();
            }catch (Exception e){
                throw new IllegalStateException("Error Generating Invoice!");
            }
        }







    private static String htmlToXhtml(String html) {
        org.jsoup.nodes.Document document = Jsoup.parse(html);
        document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
        return document.html();
    }


}
