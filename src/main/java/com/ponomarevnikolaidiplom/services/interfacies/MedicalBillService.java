package com.ponomarevnikolaidiplom.services.interfacies;

import com.ponomarevnikolaidiplom.dto.request.MedicalBillRequest;
import com.ponomarevnikolaidiplom.dto.responce.MedicalBillResponce;
import com.ponomarevnikolaidiplom.exceptions.ServiceException;

import java.util.List;

public interface MedicalBillService {

    MedicalBillResponce saveMedicalBill(MedicalBillRequest request);

    MedicalBillResponce getMedicalBill(Long id) throws ServiceException;

    List<MedicalBillResponce> getAllMedicalBills();

    String updateMedicalBill(MedicalBillRequest request) throws ServiceException;

    String deleteMedicalBill(Long id) throws ServiceException;

    String addSpecializationToMedicalBill(Long idSpecialization, Long idMedicalBill) throws ServiceException;

    String deleteSpecializationFromMedicalBill(Long idSpecialization, Long idMedicalBill) throws ServiceException;
}
