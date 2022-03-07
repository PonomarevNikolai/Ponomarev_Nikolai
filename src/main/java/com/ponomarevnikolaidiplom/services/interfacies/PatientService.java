package com.ponomarevnikolaidiplom.services.interfacies;

import com.ponomarevnikolaidiplom.dto.request.PatientRequest;
import com.ponomarevnikolaidiplom.dto.responce.PatientResponce;
import com.ponomarevnikolaidiplom.entities.Patient;

import java.util.List;

public interface PatientService {
    String savePatient(PatientRequest request);
    PatientResponce getPatient(Long id);
    List<PatientResponce> getAllPatients();
    String updatePatient(PatientRequest request);
    void deletePatient(Long id);
}
