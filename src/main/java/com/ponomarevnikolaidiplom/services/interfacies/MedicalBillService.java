package com.ponomarevnikolaidiplom.services.interfacies;

import com.ponomarevnikolaidiplom.dto.request.MedicalBillRequest;
import com.ponomarevnikolaidiplom.dto.responce.MedicalBillResponce;
import com.ponomarevnikolaidiplom.entities.MedicalBill;

import java.util.List;

public interface MedicalBillService {

    String saveMedicalBill(MedicalBillRequest request);
    MedicalBillResponce getMedicalBill(Long id);
    List<MedicalBillResponce> getAllMedicalBills();
    String updateMedicalBill(MedicalBillRequest request);
    String deleteMedicalBill(Long id);
}
