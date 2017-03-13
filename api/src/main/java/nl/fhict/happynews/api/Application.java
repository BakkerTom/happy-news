package nl.fhict.happynews.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"nl.fhict.happynews.api", "nl.fhict.happynews.shared"})
@EntityScan({"nl.fhict.happynews.api", "nl.fhict.happynews.shared"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
