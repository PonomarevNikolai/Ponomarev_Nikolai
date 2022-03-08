package com.ponomarevnikolaidiplom.services.classes;

import com.ponomarevnikolaidiplom.dto.request.SpecializationRequest;
import com.ponomarevnikolaidiplom.dto.responce.DoctorResponce;
import com.ponomarevnikolaidiplom.dto.responce.MedicalBillResponce;
import com.ponomarevnikolaidiplom.dto.responce.SpecializationResponce;
import com.ponomarevnikolaidiplom.entities.Specialization;
import com.ponomarevnikolaidiplom.repozitories.SpecializationRepository;
import com.ponomarevnikolaidiplom.services.interfacies.SpecializationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SpecializationServiceImp implements SpecializationService {

    final SpecializationRepository specializationRepository;

    @Override
    public SpecializationResponce saveSpecialization(SpecializationRequest request) {
        log.info("new Specialization added");
        return convertSpecializationToSpecializationResponce(specializationRepository.save(convertRequestToSpecialization(request)));
    }

    @Override
    public SpecializationResponce getSpecialization(Long id) {
        log.info("get Specialization by id={}", id);
        return convertSpecializationToSpecializationResponce(specializationRepository.getById(id));
    }

    @Override
    public List<SpecializationResponce> getAllSpecialization() {
        log.info("get all Specializations");
        List<SpecializationResponce> specializationResponceList = new ArrayList<>();
        specializationRepository.findAll().forEach(specialization ->
                specializationResponceList.add(convertSpecializationToSpecializationResponce(specialization)));
        return specializationResponceList;
    }

    @Override
    public String updateSpecialization(SpecializationRequest request) {
        Specialization update = specializationRepository.getById(request.getId());
        if (update == null) {
            throw new RuntimeException("Specialization not found");
        }
        if (request.getName() != null) {
            update.setName(request.getName());
        }
        specializationRepository.saveAndFlush(update);
        log.info("updated Specialization id={}", request.getId());
        return "updated Specialization  id=" + update.getId() + " name=" + request.getName();

    }

    @Override
    public String deleteSpecialization(Long id) {
        log.info("deleted Specialization id={}", id);
        specializationRepository.deleteById(id);
        return "удалена Специализация с id=" + id;
    }

    private Specialization convertRequestToSpecialization(SpecializationRequest request) {

        return new Specialization(request.getId(),
                request.getName(),
                new ArrayList<>(),
                new ArrayList<>());
    }

    private SpecializationResponce convertSpecializationToSpecializationResponce(Specialization responce) {
        List<DoctorResponce> doctorResponceList = new ArrayList<>();
        List<MedicalBillResponce> medicalBillResponceList = new ArrayList<>();
        responce.getDoctorList().forEach(doctor ->
                doctorResponceList.add(new DoctorResponce(doctor.getId(), doctor.getName()))
        );
        responce.getMedicalBillList().forEach(medicalBill ->
                medicalBillResponceList.add(new MedicalBillResponce(medicalBill.getId(),
                        medicalBill.getName(),
                        medicalBill.getPrice())));

        return new SpecializationResponce(responce.getId(),
                responce.getName(),
                doctorResponceList,
                medicalBillResponceList);
    }
}
