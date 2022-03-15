package com.ponomarevnikolaidiplom.services.interfacies;

import com.ponomarevnikolaidiplom.dto.request.DistrictRequest;
import com.ponomarevnikolaidiplom.dto.responce.DistrictResponce;
import com.ponomarevnikolaidiplom.exceptions.ServiceException;

import java.util.List;

public interface DistrictService {
    DistrictResponce saveDistrict(DistrictRequest request);

    DistrictResponce getDistrict(Long id) throws ServiceException;

    List<DistrictResponce> getAllDistricts(int page, int size);

    String updateDistrict(DistrictRequest request) throws ServiceException;

    String deleteDistrict(Long id) throws ServiceException;
}
