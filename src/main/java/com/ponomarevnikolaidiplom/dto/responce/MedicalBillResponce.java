package com.ponomarevnikolaidiplom.dto.responce;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MedicalBillResponce {
    private Long id;
    private String name;
    private Long price;
    private List<SpecializationResponce> specializationList;

    public MedicalBillResponce(Long id, String name, Long price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
}
