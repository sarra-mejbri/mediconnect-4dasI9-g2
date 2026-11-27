package tn.esprit.gardes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class Ms5GardesApplication {

    public static void main(String[] args) {
        SpringApplication.run(Ms5GardesApplication.class, args);
    }
}
