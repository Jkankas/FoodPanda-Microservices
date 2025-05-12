package com.example.foodpanda_microservices.dto.request;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentUtrRequest {
    // @JsonAlias({"TDS_Amount", "tdsAmount"})
	// String tdsAmount;
//	@JsonAlias({"UTR_Number", "utrNumber"})
	private String utrNumber;
	// @JsonAlias({"Amount_Paid", "amountPaid"})
	// String amountPaid;
	// @JsonAlias({"Vendor_Code", "vendorCode"})
	// String vendorCode;
	// @JsonAlias({"Vendor_Name", "vendorName"})
	// String vendorName;
	// @JsonAlias({"Gross_Amount", "grossAmount"})
	// String grossAmount;
	// @JsonAlias({"Invoice_Date", "invoiceDate"})
	// String invoiceDate;
	// @JsonAlias({"Payment_Date", "paymentDate"})
	// String paymentDate;
	// @JsonAlias({"Invoice_Group", "invoiceGroup"})
	// String invoiceGroup;
	// @JsonAlias({"Failure_Reason", "failureReason"})
	// String failureReason;
	// @JsonAlias({"Invoice_Number", "invoiceNumber"})
	// String invoiceNumber;
	// @JsonAlias({"Payment_Status", "paymentStatus"})
	// String paymentStatus;
	// @JsonAlias({"ERP_Document_Number", "erpDocumentNumber"})
	// String erpDocumentNumber;
	// @JsonAlias({"Payment_Document_Number", "paymentDocumentNumber"})
	// String paymentDocumentNumber;
}
