package com.ponomarevnikolaidiplom.dto.request;

import lombok.Data;

@Data
public class MedicalBillRequest {

    private Long id;
    private final String name;
    private final Long price;

}
