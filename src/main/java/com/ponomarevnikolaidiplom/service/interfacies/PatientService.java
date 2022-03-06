package com.ponomarevnikolaidiplom.service.interfacies;

import com.ponomarevnikolaidiplom.dto.request.PatientRequest;
import com.ponomarevnikolaidiplom.entities.Patient;

import java.util.List;

public interface PatientService {
    String savePatient(PatientRequest request);
    Patient getPatient(Long id);
    List<Patient> getAllPatients();
    void updatePatient(PatientRequest request);
    void deletePatient(Long id);
}
