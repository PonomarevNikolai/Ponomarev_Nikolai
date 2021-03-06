package com.ponomarevnikolaidiplom.dto.responce;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SpecializationResponce {
    private Long id;
    private String name;
    private List<String> doctorResponceList;
    private List<String> medicalBillResponceList;

    public SpecializationResponce(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
