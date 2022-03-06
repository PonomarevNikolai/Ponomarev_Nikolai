package com.ponomarevnikolaidiplom.dto.request;

import lombok.Data;


@Data
public class PatientRequest {
    private final Long id;
    private final String numberOfInsurance;
    private final String name;
    private final String phoneNumber;
    private final String address;
}
