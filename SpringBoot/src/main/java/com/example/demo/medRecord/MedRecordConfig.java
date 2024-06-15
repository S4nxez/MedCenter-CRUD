package com.example.demo.medRecord;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class MedRecordConfig {
    @Bean
    CommandLineRunner medCommandLineRunner(
            MedRecordRepository repository
    ){
        return args -> {
            MedRecord med1 = new MedRecord(
                    "Hepatitis",
                    LocalDate.now(),
                    1L,
                    2L,
                    "Hepsera"
            );
            repository.saveAll(List.of(med1));
        };
    }
}
