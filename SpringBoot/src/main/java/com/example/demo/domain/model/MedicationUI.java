package com.example.demo.domain.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MedicationUI {

    private int id;
    private String medicationName;
    private int medRecordId;
}
