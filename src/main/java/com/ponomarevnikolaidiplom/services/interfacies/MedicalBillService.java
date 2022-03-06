package com.ponomarevnikolaidiplom.services.interfacies;

import com.ponomarevnikolaidiplom.dto.request.MedicalBillRequest;
import com.ponomarevnikolaidiplom.entities.MedicalBill;

import java.util.List;

public interface MedicalBillService {

    String saveMedicalBill(MedicalBillRequest request);
    MedicalBill getMedicalBill(Long id);
    List<MedicalBill> getAllMedicalBills();
    void updateMedicalBill(MedicalBillRequest request);
    void deleteMedicalBill(Long id);
}
