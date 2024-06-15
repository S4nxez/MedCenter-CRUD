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
           Patient john = new Patient(
                    1L,
                    "John Smith",
                    "666 23 23 12"

            );
            Patient dani = new Patient(
                    2L,
                    "Dani Sánchez",
                    "601 23 12 12"
            );
            repository.saveAll(List.of(john, dani));
        };
    }
}
