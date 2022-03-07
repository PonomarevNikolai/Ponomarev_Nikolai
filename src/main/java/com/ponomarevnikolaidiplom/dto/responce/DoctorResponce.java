package com.ponomarevnikolaidiplom.dto.responce;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class DoctorResponce {

    private Long id;
    private String name;
    private List<SpecializationResponce> specializationList;

    public DoctorResponce(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}