package com.example.demo.medRecord;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "medical_record")
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
    private Long idPatient;
    private Long idDoctor;
    private String medications;

    public MedRecord(String description, LocalDate date, Long idPatient, Long idDoctor, String medications) {
        this.description = description;
        this.date = date;
        this.idPatient = idPatient;
        this.idDoctor = idDoctor;
        this.medications = medications;
    }

    @Override
    public String toString() {
        return "MedRecord{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", idPatient=" + idPatient +
                ", idDoctor=" + idDoctor +
                ", medications='" + medications + '\'' +
                '}';
    }
}
