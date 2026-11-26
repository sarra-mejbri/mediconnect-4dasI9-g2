package tn.esprit.traitements;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class Ms4TraitementsApplication {
    public static void main(String[] args) {
        SpringApplication.run(Ms4TraitementsApplication.class, args);
    }
}