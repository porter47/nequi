package co.com.nequi.franchises.infrastructure;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "co.com.nequi.franchises")
public class NequiFranchisesApplication {

	public static void main(String[] args) {
		SpringApplication.run(NequiFranchisesApplication.class, args);
	}

}
