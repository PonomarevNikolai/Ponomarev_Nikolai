package com.ponomarevnikolaidiplom.services.interfacies;

import com.ponomarevnikolaidiplom.dto.request.DistrictRequest;
import com.ponomarevnikolaidiplom.dto.responce.DistrictResponce;

import java.util.List;

public interface DistrictService {
    DistrictResponce saveDistrict(DistrictRequest request);

    DistrictResponce getDistrict(Long id);

    List<DistrictResponce> getAllDistricts();

    String updateDistrict(DistrictRequest request);

    String deleteDistrict(Long id);
}
