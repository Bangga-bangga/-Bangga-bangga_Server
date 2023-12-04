package Banggabanggacom.example.Banggabangga;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BanggaBanggaApplication {

	public static void main(String[] args) {
		SpringApplication.run(BanggaBanggaApplication.class, args);
	}

}
