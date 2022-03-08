package com.ponomarevnikolaidiplom.services.interfacies;

import com.ponomarevnikolaidiplom.dto.request.PatientRequest;
import com.ponomarevnikolaidiplom.dto.responce.PatientResponce;

import java.util.List;

public interface PatientService {
    PatientResponce savePatient(PatientRequest request);
    PatientResponce getPatient(Long id);
    List<PatientResponce> getAllPatients();
    String updatePatient(PatientRequest request);
    void deletePatient(Long id);
}
