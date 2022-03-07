package com.ponomarevnikolaidiplom.dto.responce;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PatientResponce {

    private Long id;
    private String numberOfInsurance;
    private String name;
    private String phoneNumber;
    private String address;
}
