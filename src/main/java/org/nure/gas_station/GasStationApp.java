package org.nure.gas_station;
import lombok.AllArgsConstructor;
import org.nure.gas_station.exceptions.EntityAlreadyExistsException;
import org.nure.gas_station.model.Fuel;
import org.nure.gas_station.model.FuelTariff;
import org.nure.gas_station.model.enumerations.OrderType;
import org.nure.gas_station.model.enumerations.UserRoles;
import org.nure.gas_station.services.interfaces.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.sql.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;


@SpringBootApplication
@ComponentScan("org.nure")
@EnableJpaRepositories(basePackages = "org.nure.gas_station.repositories")
public class GasStationApp {

    @Value("${admin.settings.username}")
    private String adminUsername;
    @Value("${admin.settings.password}")
    private String adminPassword;
    @Value("${admin.settings.name}")
    private String adminName;
    @Value("${admin.settings.surname}")
    private String adminSurname;
    @Value("${default.data.generate}")
    private boolean isGenerateDefaultData;

    @Autowired
    private IUserService userService;
    @Autowired
    private IAdminService adminService;
    @Autowired
    private IFuelService fuelService;
    @Autowired
    private IFuelTariffService fuelTariffService;
    @Autowired
    private IFuelOrderService fuelOrderService;

    public static void main(String[] args) {
        SpringApplication.run(GasStationApp.class, args);
    }

    @PostConstruct
    public void startup() throws Exception {
        System.out.println(String.format("Generating admin with username=%s, password=%s, name=%s, surname=%s", adminUsername, adminPassword, adminName, adminSurname));
        try {
            userService.createUser(adminUsername, adminPassword, adminName, adminSurname, UserRoles.ROLE_ADMIN);
        } catch (EntityAlreadyExistsException ex) {
            System.out.println("User with this name already exists in the database, changing his authority to admin");
            adminService.setRole(adminUsername, UserRoles.ROLE_ADMIN);
        }
        if (isGenerateDefaultData) {
            System.out.println("Generating default data");
            String username1 = "batsinpants";
            String username2 = "randomuser";
            try {
                userService.createUser(username1, "pass1234", "Alex", "Svir", UserRoles.ROLE_BUYER);
            } catch (EntityAlreadyExistsException ex) {
                System.out.println(String.format("User by username %s already exists", username1));
            }
            try {
                userService.createUser(username2, "pass1234", "Pepega", "Forsaan", UserRoles.ROLE_BUYER);
            } catch (EntityAlreadyExistsException ex) {
                System.out.println(String.format("User by username %s already exists", username2));
            }
            float exchangeRate1 = 18;
            float exchangeRate2 = 15;
            FuelTariff fuelTariff1 = fuelTariffService.createFuelTariff(exchangeRate1);
            FuelTariff fuelTariff2 = fuelTariffService.createFuelTariff(exchangeRate2);
            String fuelName1 = "95";
            String fuelName2 = "92";
            try {
                fuelService.addFuel(fuelName1, fuelTariff1.getId(), 5000);
            } catch (EntityAlreadyExistsException ex) {
                System.out.println(String.format("Fuel by name %s already exists", fuelName1));
            }
            try {
                fuelService.addFuel(fuelName2, fuelTariff2.getId(), 8000);
            } catch (EntityAlreadyExistsException ex) {
                System.out.println(String.format("Fuel by name %s already exists", fuelName2));
            }
            Fuel fuel1 = fuelService.getFuel(fuelName1);
            Fuel fuel2 = fuelService.getFuel(fuelName2);
            fuelOrderService.orderFuel(username1, fuelName1, 2, OrderType.CURRENCY_BY_FUEL, Date.from(Instant.now().plus(3, ChronoUnit.DAYS)));
            fuelOrderService.orderFuel(username1, fuelName2, 1, OrderType.CURRENCY_BY_FUEL, Date.from(Instant.now().plus(19, ChronoUnit.DAYS)));
            fuelOrderService.orderFuel(username1, fuelName1, 5, OrderType.FUEL_BY_CURRENCY, Date.from(Instant.now().plus(40, ChronoUnit.DAYS)));
            fuelOrderService.orderFuel(username2, fuelName2, 2, OrderType.CURRENCY_BY_FUEL, Date.from(Instant.now().plus(3, ChronoUnit.DAYS)));
            fuelOrderService.orderFuel(username2, fuelName2, 1, OrderType.FUEL_BY_CURRENCY, Date.from(Instant.now().plus(22, ChronoUnit.DAYS)));
            fuelOrderService.orderFuel(username2, fuelName1, 5, OrderType.FUEL_BY_CURRENCY, Date.from(Instant.now().plus(45, ChronoUnit.DAYS)));
            System.out.println("Default data generation completed");
        }
    }
}
