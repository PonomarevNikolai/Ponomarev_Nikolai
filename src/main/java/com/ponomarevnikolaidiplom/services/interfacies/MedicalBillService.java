package com.ponomarevnikolaidiplom.services.interfacies;

import com.ponomarevnikolaidiplom.dto.request.MedicalBillRequest;
import com.ponomarevnikolaidiplom.dto.responce.MedicalBillResponce;

import java.util.List;

public interface MedicalBillService {

    MedicalBillResponce saveMedicalBill(MedicalBillRequest request);

    MedicalBillResponce getMedicalBill(Long id);

    List<MedicalBillResponce> getAllMedicalBills();

    String updateMedicalBill(MedicalBillRequest request);

    String deleteMedicalBill(Long id);

    String addSpecializationToMedicalBill(Long idSpecialization, Long idMedicalBill);

    String deleteSpecializationFromMedicalBill(Long idSpecialization, Long idMedicalBill);
}
