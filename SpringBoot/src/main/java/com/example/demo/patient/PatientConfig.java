package com.example.demo.patient;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class PatientConfig {

    @Bean
    CommandLineRunner patientCommandLineRunner(
            PatientRepository repository
    ){
        return ars ->{
           Patient eduardo = new Patient(
                    1L,
                    "Eduardo García",
                    "666 23 23 12"

            );
            Patient juanjo = new Patient(
                    2L,
                    "Juan José Fernández",
                    "601 23 12 12"
            );
            Patient carlos = new Patient(
                    3L,
                    "Carlos Lopez",
                    "644 55 55 55"
            );
            Patient pedro = new Patient(
                    4L,
                    "Pedro García",
                    "633 44 44 44"
            );
            Patient daniel = new Patient(
                    5L,
                    "Daniel Sánchez",
                    "622 33 33 33"
            );
            repository.saveAll(List.of(eduardo, juanjo, carlos, pedro, daniel));
        };
    }
}
