package com.example.demo.dao.repositories.impl;

import com.example.demo.dao.repositories.MedRecordRepositoryInt;
import com.example.demo.domain.model.MedRecordUI;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class MedRecordRepository {
    @Bean
    CommandLineRunner medCommandLineRunner(
            MedRecordRepositoryInt repository
    ){
        return args -> {
            MedRecordUI med1 = new MedRecordUI(
                    "Hepatitis",
                    LocalDate.now(),
                    1L,
                    2L,
                    "Hepsera"
            );
            MedRecordUI med2 = new MedRecordUI(
                    "Diabetes",
                    LocalDate.now(),
                    2L,
                    4L,
                    "Metformin"
            );
            MedRecordUI med3 = new MedRecordUI(
                    "Hypertension",
                    LocalDate.now(),
                    3L,
                    5L,
                    "Lisinopril"
            );
            MedRecordUI med4 = new MedRecordUI(
                    "Asthma",
                    LocalDate.now(),
                    4L,
                    3L,
                    "Ventolin"
            );
            MedRecordUI med5 = new MedRecordUI(
                    "COVID-19",
                    LocalDate.now(),
                    5L,
                    1L,
                    "Remdesivir"
            );
            MedRecordUI med6 = new MedRecordUI(
                    "Pneumonia",
                    LocalDate.now(),
                    3L,
                    2L,
                    "Zithromax"
            );
            MedRecordUI med7 = new MedRecordUI(
                    "Arthritis",
                    LocalDate.now(),
                    4L,
                    4L,
                    "Humira"
            );
            MedRecordUI med8 = new MedRecordUI(
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
