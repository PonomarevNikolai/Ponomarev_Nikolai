package com.ponomarevnikolaidiplom.services.interfacies;

import com.ponomarevnikolaidiplom.dto.request.SpecializationRequest;
import com.ponomarevnikolaidiplom.dto.responce.SpecializationResponce;
import com.ponomarevnikolaidiplom.exceptions.ServiceException;

import java.util.List;

public interface SpecializationService {
    SpecializationResponce saveSpecialization(SpecializationRequest request);

    SpecializationResponce getSpecialization(Long id) throws ServiceException;

    List<SpecializationResponce> getAllSpecialization();

    String updateSpecialization(SpecializationRequest request) throws ServiceException;

    String deleteSpecialization(Long id) throws ServiceException;
}
