package com.ponomarevnikolaidiplom.services.interfacies;

import com.ponomarevnikolaidiplom.dto.request.SpecializationRequest;
import com.ponomarevnikolaidiplom.entities.Specialization;

import java.util.List;

public interface SpecializationService {
   Specialization saveSpecialization(SpecializationRequest request);
   Specialization getSpecialization(Long id);
   List<Specialization> getAllSpecialization();
   void updateSpecialization (SpecializationRequest request);
    void deleteSpecialization(Long id);
}
