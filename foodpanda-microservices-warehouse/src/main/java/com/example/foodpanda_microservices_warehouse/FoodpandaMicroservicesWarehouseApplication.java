package com.example.foodpanda_microservices_warehouse;

import com.example.foodpanda_microservices_warehouse.dto.response.ApiResponse;
import com.example.foodpanda_microservices_warehouse.entity.Price;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import lombok.Data;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.sql.*;
import java.util.Map;
import java.util.Set;

@Data
@SpringBootApplication
public class FoodpandaMicroservicesWarehouseApplication {


//	@Value("${spring.datasource.url}")
//	private String DB_URL;
//	@Value("${spring.datasource.username}")
//	private String DB_USER;
//	@Value("${spring.datasource.password}")
//	private String DB_PASSWORD;


	public static void main(String[] args) throws SQLException {

		SpringApplication.run(FoodpandaMicroservicesWarehouseApplication.class, args);






//		ConfigurableApplicationContext context = SpringApplication.run(FoodpandaMicroservicesWarehouseApplication.class, args);
//		FoodpandaMicroservicesWarehouseApplication application = context.getBean(FoodpandaMicroservicesWarehouseApplication.class);


		/* --------- WITHOUT HIBERNATE-----------*/

//		try{
//		Connection connection = DriverManager.getConnection(application.DB_URL,application.DB_USER,application.DB_PASSWORD);

		// CREATE A NEW RECORD
//		String insertQuery= "insert into dish_price(dish,price) values (?,?)";
//		PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
//		preparedStatement.setString(1,"Vada Pao");
//		preparedStatement.setDouble(2,99.0);
//		int rowsAffected = preparedStatement.executeUpdate();
//		System.out.println("Inserted " + rowsAffected + " rows.");


		// RETRIEVES A RECORD
//		String selectQuery = "select * from dish_price where dish = ?";
//		PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
//		preparedStatement.setString(1,"Vada Pao");
//		ResultSet rs = preparedStatement.executeQuery();
//		while (rs.next()){
//			Long id = rs.getLong("id");
//			String dish = rs.getNString("dish");
//			double price = rs.getDouble("price");
//			System.out.println(id + ": " + dish + " ->(" + price + ")");
//		}
//
//		}catch (SQLException ex){
//		ex.printStackTrace();
//	}




//			EntityManagerFactory emf = Persistence.createEntityManagerFactory("myPersistenceUnit");
//			EntityManager em = emf.createEntityManager();
//			EntityTransaction tx = em.getTransaction();
//
//		try{
//			tx.begin();
//
//			// Create a new Record
//			Price price = Price.builder().dish("Egg Roll").price(100.0).build();
//			em.persist(price);
//
//		}catch (Exception e){
//			e.printStackTrace();
//		}
		/* --------- WITH HIBERNATE-----------*/
//		finally {
//			em.close();
//			emf.close();
//		}
//
//
//	}

	}
}