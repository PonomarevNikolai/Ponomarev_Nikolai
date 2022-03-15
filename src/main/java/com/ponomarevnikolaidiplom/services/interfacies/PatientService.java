package com.ponomarevnikolaidiplom.services.interfacies;

import com.ponomarevnikolaidiplom.dto.request.PatientRequest;
import com.ponomarevnikolaidiplom.dto.responce.PatientResponce;
import com.ponomarevnikolaidiplom.exceptions.ServiceException;

import java.util.List;

public interface PatientService {
    PatientResponce savePatient(PatientRequest request) throws ServiceException;
    PatientResponce getPatient(Long id) throws ServiceException;
    List<PatientResponce> getAllPatients(int page, int size);
    String updatePatient(PatientRequest request) throws ServiceException;
    void deletePatient(Long id) throws ServiceException;
}
