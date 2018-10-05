package org.nure.GasStation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("org.nure")
public class GasStationApp {

    public static void main(String[] args) {
        SpringApplication.run(GasStationApp.class, args);
    }
}
