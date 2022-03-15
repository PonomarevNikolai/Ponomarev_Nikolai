package com.ponomarevnikolaidiplom.services.classes;

import com.ponomarevnikolaidiplom.dto.request.MedicalBillRequest;
import com.ponomarevnikolaidiplom.dto.responce.MedicalBillResponce;
import com.ponomarevnikolaidiplom.entities.MedicalBill;
import com.ponomarevnikolaidiplom.entities.Specialization;
import com.ponomarevnikolaidiplom.exceptionhandler.TypicalError;
import com.ponomarevnikolaidiplom.exceptions.ServiceException;
import com.ponomarevnikolaidiplom.repozitories.MedicalBillRepository;
import com.ponomarevnikolaidiplom.repozitories.SpecializationRepository;
import com.ponomarevnikolaidiplom.services.interfacies.MedicalBillService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MedicalBillServiceImp implements MedicalBillService {

    final MedicalBillRepository medicalBillRepository;
    final SpecializationRepository specializationRepository;
    private static final String MEDICALBILLNOTFOUND = "MedicalBill not found";

    @Override
    public MedicalBillResponce saveMedicalBill(MedicalBillRequest request) {
        log.info("new MedicalBill added");
        return convertMedicalBillToMedicalBillResponce(medicalBillRepository.save(convertRequestToMedicalBill(request)));
    }

    @Override
    public MedicalBillResponce getMedicalBill(Long id) throws ServiceException {
        Optional<MedicalBill> request = medicalBillRepository.findById(id);
        if (request.isEmpty()) {

            throw new ServiceException(MEDICALBILLNOTFOUND, TypicalError.NOT_FOUND);
        }
        log.info("get MedicalBill by id={}", id);
        return convertMedicalBillToMedicalBillResponce(medicalBillRepository.findMedicalBillById(id));
    }

    @Override
    public List<MedicalBillResponce> getAllMedicalBills(int page, int size) {
        Page<MedicalBill> medicalBillPage=medicalBillRepository.findAll(PageRequest.of(page,size));
        List<MedicalBillResponce> medicalBillResponceList = new ArrayList<>();
        medicalBillRepository.findAll().forEach(medicalBill ->
                medicalBillResponceList.add(convertMedicalBillToMedicalBillResponce(medicalBill)));
        log.info("get all MedicalBills ");
        return medicalBillResponceList;
    }

    @Override
    public String updateMedicalBill(MedicalBillRequest request) throws ServiceException {
        if (request.getId() == null) {
            throw new ServiceException("MedicalBill bad request id is null", TypicalError.BAD_REQUEST);
        }
        Optional<MedicalBill> medicalBillOptional = medicalBillRepository.findById(request.getId());
        if (medicalBillOptional.isEmpty()) {
            throw new ServiceException(MEDICALBILLNOTFOUND, TypicalError.NOT_FOUND);
        }
        MedicalBill update = medicalBillRepository.getById(request.getId());

        if (request.getName() != null) {
            update.setName(request.getName());
        }
        if (request.getPrice() != null) {
            update.setPrice(request.getPrice());
        }

        medicalBillRepository.saveAndFlush(update);
        log.info("updated MedicalBill id={} to name={}", update.getId(), update.getName());
        return "Обновлена услуга с id=" + update.getId() + " name=" + update.getName();
    }

    @Override
    public String deleteMedicalBill(Long id) throws ServiceException {
        Optional<MedicalBill> medicalBillOptional = medicalBillRepository.findById(id);
        if (medicalBillOptional.isEmpty()) {
            throw new ServiceException(MEDICALBILLNOTFOUND, TypicalError.NOT_FOUND);
        }
        log.info("deleted MedicalBill id={}", id);
        medicalBillRepository.deleteById(id);
        return "deleted MedicalBill id=" + id;
    }

    @Override
    public String addSpecializationToMedicalBill(Long idSpecialization, Long idMedicalBill) throws ServiceException {
        Optional<MedicalBill> medicalBillOptional = medicalBillRepository.findById(idMedicalBill);

        if (medicalBillOptional.isEmpty()) {
            throw new ServiceException(MEDICALBILLNOTFOUND, TypicalError.NOT_FOUND);
        }

        Optional<Specialization> specializationOptional = specializationRepository.findById(idSpecialization);

        if (specializationOptional.isEmpty()) {
            throw new ServiceException("Specialization not found", TypicalError.NOT_FOUND);
        }
        MedicalBill medicalBill = medicalBillRepository.findMedicalBillById(idMedicalBill);
        Specialization specialization = specializationRepository.getById(idSpecialization);
        medicalBill.getSpecializationList().add(specialization);
        specialization.getMedicalBillList().add(medicalBill);
        medicalBillRepository.saveAndFlush(medicalBill);
        specializationRepository.saveAndFlush(specialization);
        return "Specialization name = " + specialization.getName() + " added to medicalBill name = " + medicalBill.getName();
    }

    @Override
    public String deleteSpecializationFromMedicalBill(Long idSpecialization, Long idMedicalBill) throws ServiceException {
        Optional<MedicalBill> medicalBillOptional = medicalBillRepository.findById(idMedicalBill);

        if (medicalBillOptional.isEmpty()) {
            throw new ServiceException(MEDICALBILLNOTFOUND, TypicalError.NOT_FOUND);
        }

        Optional<Specialization> specializationOptional = specializationRepository.findById(idSpecialization);

        if (specializationOptional.isEmpty()) {
            throw new ServiceException("Specialization not found", TypicalError.NOT_FOUND);
        }

        MedicalBill medicalBill = medicalBillRepository.findMedicalBillById(idMedicalBill);
        Specialization specialization = specializationRepository.getById(idSpecialization);
        specialization.getMedicalBillList().remove(medicalBill);
        medicalBill.getSpecializationList().remove(specialization);
        medicalBillRepository.saveAndFlush(medicalBill);
        specializationRepository.saveAndFlush(specialization);
        return "Specialization name = " + specialization.getName() + " deleted from MedicalBill name=" + medicalBill.getName();
    }

    private MedicalBill convertRequestToMedicalBill(MedicalBillRequest request) {

        return new MedicalBill(request.getId(), request.getName(), request.getPrice(), new ArrayList<>());
    }

    private MedicalBillResponce convertMedicalBillToMedicalBillResponce(MedicalBill responce) {
        List<String> specializationList = new ArrayList<>();
        responce.getSpecializationList().forEach(specialization ->
                specializationList.add("id=" + specialization.getId() + ", name=" + specialization.getName()));
        return new MedicalBillResponce(responce.getId(),
                responce.getName(),
                responce.getPrice(),
                specializationList);
    }
}
