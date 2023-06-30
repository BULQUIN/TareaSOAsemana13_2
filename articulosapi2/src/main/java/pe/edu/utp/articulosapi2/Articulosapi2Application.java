package pe.edu.utp.articulosapi2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Articulosapi2Application {

	public static void main(String[] args) {
		SpringApplication.run(Articulosapi2Application.class, args);
	}

}
