package com.ponomarevnikolaidiplom.services.classes;

import com.ponomarevnikolaidiplom.dto.request.MedicalBillRequest;
import com.ponomarevnikolaidiplom.entities.MedicalBill;
import com.ponomarevnikolaidiplom.repozitories.MedicalBillRepository;
import com.ponomarevnikolaidiplom.services.interfacies.MedicalBillService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MedicalBillServiceImp implements MedicalBillService {

    final MedicalBillRepository medicalBillRepository;

    @Override
    public String saveMedicalBill(MedicalBillRequest request) {
        medicalBillRepository.save(convertRequestToMedicalBill(request));
        log.info("new MedicalBill added");
        return request.getName();
    }

    @Override
    public MedicalBill getMedicalBill(Long id) {
        log.info("get MedicalBill by id={}", id);
        return medicalBillRepository.findMedicalBillById(id);
    }

    @Override
    public List<MedicalBill> getAllMedicalBills() {
        log.info("get all MedicalBills ");
        return medicalBillRepository.findAll();
    }

    @Override
    public String updateMedicalBill(MedicalBillRequest request) {
       MedicalBill update = medicalBillRepository.getById(request.getId());
        if (update == null) {
            throw new RuntimeException("MedicalBill not found");
        }
        if (request.getName() != null) {
            update.setName(request.getName());
        }
        if (request.getPrice()!=null){
            update.setPrice(request.getPrice());
        }

       medicalBillRepository.saveAndFlush(update);
        log.info("updated MedicalBill id={} to name={}", request.getId(), request.getName());
        return "Обновлена услуга с id="+update.getId()+" name="+request.getName();
    }

    @Override
    public void deleteMedicalBill(Long id) {
        log.info("deleted MedicalBill id={}", id);
        medicalBillRepository.deleteById(id);

    }
    private MedicalBill convertRequestToMedicalBill(MedicalBillRequest request) {

        return new MedicalBill(request.getId(), request.getName(), request.getPrice(), new ArrayList<>());
    }
}
