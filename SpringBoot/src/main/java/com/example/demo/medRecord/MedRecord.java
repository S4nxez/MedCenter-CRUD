package com.example.demo.medRecord;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "medicalRecord")
public class MedRecord {
    @Id
    @SequenceGenerator(
            name = "medRecord_sequence",
            sequenceName = "medRecord_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "medRecord_sequence"
    )
    private Long id;
    private String description;
    private LocalDate date;
    private int idDoctor;
    private String medications;

    public MedRecord(String description, LocalDate date, int idDoctor, String medications) {
        this.description = description;
        this.date = date;
        this.idDoctor = idDoctor;
        this.medications = medications;
    }

    @Override
    public String toString() {
        return "MedRecord{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", idDoctor=" + idDoctor +
                ", medications='" + medications + '\'' +
                '}';
    }
}
