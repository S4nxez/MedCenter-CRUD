package com.example.demo.dao.repositories.impl;

import com.example.demo.dao.repositories.PatientRepositoryInt;
import com.example.demo.domain.model.PatientUI;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class PatientRepository {

    @Bean
    CommandLineRunner patientCommandLineRunner(
            PatientRepositoryInt repository
    ){
        return ars ->{
           PatientUI eduardo = new PatientUI(
                    1L,
                    "Eduardo García",
                    LocalDate.of(2000,12,3),
                    "666 23 23 12",
                   43,
                   "passwrd",
                   "username"
            );
            PatientUI juanjo = new PatientUI(
                    2L,
                    "Juanjo Fernández",
                    LocalDate.of(2002, 2, 3),
                    "601 23 12 12",
                    23,
                    "passwrd",
                    "username"
            );
            PatientUI carlos = new PatientUI(
                    3L,
                    "Carlos Lopez",
                    LocalDate.of(1990,1,2),
                    "644 55 55 55",
                    0,
                    null,
                    null
            );
            PatientUI pedro = new PatientUI(
                    4L,
                    "Pedro García",
                    LocalDate.of(1999, 3,2),
                    "633 44 44 44",
                    3,
                    null,
                    null
            );
            PatientUI daniel = new PatientUI(
                    5L,
                    "Daniel Sánchez",
                    LocalDate.now(),
                    "622 33 33 33",
                    0,
                    "passwrd",
                    "username"
            );
            repository.saveAll(List.of(eduardo, juanjo, carlos, pedro, daniel));
        };
    }
}
