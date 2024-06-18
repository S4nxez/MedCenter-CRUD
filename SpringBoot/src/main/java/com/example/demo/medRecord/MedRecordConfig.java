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
            MedRecord med2 = new MedRecord(
                    "Diabetes",
                    LocalDate.now(),
                    2L,
                    4L,
                    "Metformin"
            );
            MedRecord med3 = new MedRecord(
                    "Hypertension",
                    LocalDate.now(),
                    3L,
                    5L,
                    "Lisinopril"
            );
            MedRecord med4 = new MedRecord(
                    "Asthma",
                    LocalDate.now(),
                    4L,
                    3L,
                    "Ventolin"
            );
            MedRecord med5 = new MedRecord(
                    "COVID-19",
                    LocalDate.now(),
                    5L,
                    1L,
                    "Remdesivir"
            );
            MedRecord med6 = new MedRecord(
                    "Pneumonia",
                    LocalDate.now(),
                    3L,
                    2L,
                    "Zithromax"
            );
            MedRecord med7 = new MedRecord(
                    "Arthritis",
                    LocalDate.now(),
                    4L,
                    4L,
                    "Humira"
            );
            MedRecord med8 = new MedRecord(
                    "Migraine",
                    LocalDate.now(),
                    1L,
                    3L,
                    "Imitrex"
            );
            repository.saveAll(List.of(med1, med2, med3, med4, med5, med6, med7, med8));
        };
    }
}
