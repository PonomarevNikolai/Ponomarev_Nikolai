package com.ponomarevnikolaidiplom.services.classes;

import com.ponomarevnikolaidiplom.dto.request.SpecializationRequest;
import com.ponomarevnikolaidiplom.entities.Specialization;
import com.ponomarevnikolaidiplom.repozitories.SpecializationRepository;
import com.ponomarevnikolaidiplom.services.interfacies.SpecializationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SpecializationServiceImp implements SpecializationService {

    final SpecializationRepository specializationRepository;

    @Override
    public Specialization saveSpecialization(SpecializationRequest request) {
        log.info("new Specialization added");
        return specializationRepository.save(convertRequestToSpecialization(request));
    }

    @Override
    public Specialization getSpecialization(Long id) {
        log.info("get Specialization by id={}", id);
        return specializationRepository.getById(id);
    }

    @Override
    public List<Specialization> getAllSpecialization() {
        log.info("get all Specializations");
        return specializationRepository.findAll();
    }

    @Override
    public void updateSpecialization(SpecializationRequest request) {
        Specialization update = specializationRepository.getById(request.getId());
        if (update == null) {
            throw new RuntimeException("Patient not found");
        }
        if (request.getName() == null) {
            update.setName(request.getName());
        }
        specializationRepository.save(update);
        log.info("updated Specialization id={}", request.getId());

    }

    @Override
    public void deleteSpecialization(Long id) {
        log.info("deleted Specialization id={}", id);
        specializationRepository.deleteById(id);
    }

    private Specialization convertRequestToSpecialization(SpecializationRequest request) {

        return new Specialization(request.getId(),
                request.getName(),
                new ArrayList<>());
    }
}
