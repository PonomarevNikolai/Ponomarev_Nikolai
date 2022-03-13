package com.ponomarevnikolaidiplom.dto.responce;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DistrictResponce {
    private Long id;
    private String name;
    private String districtDoctor;
}
