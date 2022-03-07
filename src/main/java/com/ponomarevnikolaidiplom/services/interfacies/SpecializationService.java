package com.ponomarevnikolaidiplom.services.interfacies;

import com.ponomarevnikolaidiplom.dto.request.SpecializationRequest;
import com.ponomarevnikolaidiplom.dto.responce.SpecializationResponce;
import com.ponomarevnikolaidiplom.entities.Specialization;

import java.util.List;

public interface SpecializationService {
    Specialization saveSpecialization(SpecializationRequest request);

    SpecializationResponce getSpecialization(Long id);

    List<SpecializationResponce> getAllSpecialization();

    String updateSpecialization(SpecializationRequest request);

    String deleteSpecialization(Long id);
}
