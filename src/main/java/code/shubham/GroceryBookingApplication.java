package code.shubham;

import code.shubham.commons.annotations.SpringBootJpaApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootJpaApplication
public class GroceryBookingApplication {

	public static void main(String[] args) {
		SpringApplication.run(GroceryBookingApplication.class, args);
	}

}
