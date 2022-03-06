package com.ponomarevnikolaidiplom.service.classes;

import com.ponomarevnikolaidiplom.dto.request.PatientRequest;
import com.ponomarevnikolaidiplom.entities.Patient;
import com.ponomarevnikolaidiplom.repozitories.PatientRepository;
import com.ponomarevnikolaidiplom.service.interfacies.PatientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PatientServiceImp implements PatientService {

    final PatientRepository patientRepository;

    public String savePatient(PatientRequest request) {
        log.info("new Patient added");
        patientRepository.save(convertRequestToPatient(request));
        return request.getName();
    }

    @Override
    public Patient getPatient(Long id) {
        log.info("get Patient by id");
        return patientRepository.getById(id);
    }

    @Override
    public List<Patient> getAllPatients() {
        log.info("get all Patients ");
        return patientRepository.findAll();
    }

    @Override
    public void updatePatient(PatientRequest request) {
        Patient update = patientRepository.getById(request.getId());
        if (update == null) {
            throw new RuntimeException("Patient not found");
        }
        if (request.getName() == null) {
            update.setName(request.getName());
        }
        if (request.getNumberOfInsurance() == null) {
            update.setNumberOfInsurance(request.getNumberOfInsurance());
        }

        if (request.getAddress() == null) {
            update.setAddress(request.getAddress());
        }

        if (request.getPhoneNumber() == null) {
            update.setPhoneNumber(request.getPhoneNumber());
        }

        patientRepository.save(update);
        log.info("updated Patient id={}", request.getId());

    }

    @Override
    public void deletePatient(Long id) {
        log.info("deleted Patient id={}", id);
        patientRepository.deleteById(id);

    }

    private Patient convertRequestToPatient(PatientRequest request) {

        return new Patient(request.getId(),
                request.getNumberOfInsurance(),
                request.getName(),
                request.getPhoneNumber(),
                request.getAddress());
    }
}
