package com.ponomarevnikolaidiplom.services.classes;

import com.ponomarevnikolaidiplom.dto.request.SpecializationRequest;
import com.ponomarevnikolaidiplom.dto.responce.SpecializationResponce;
import com.ponomarevnikolaidiplom.entities.Specialization;
import com.ponomarevnikolaidiplom.exceptionhandler.TypicalError;
import com.ponomarevnikolaidiplom.exceptions.ServiceException;
import com.ponomarevnikolaidiplom.repozitories.SpecializationRepository;
import com.ponomarevnikolaidiplom.services.interfacies.SpecializationService;
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
public class SpecializationServiceImp implements SpecializationService {

    final SpecializationRepository specializationRepository;

    @Override
    public SpecializationResponce saveSpecialization(SpecializationRequest request) {
        log.info("new Specialization added");
        return convertSpecializationToSpecializationResponce(specializationRepository.save(convertRequestToSpecialization(request)));
    }

    @Override
    public SpecializationResponce getSpecialization(Long id) throws ServiceException {
        Optional<Specialization> specialization= specializationRepository.findById(id);
        if(specialization.isEmpty()){
            throw new ServiceException("Specialization not found",TypicalError.NOT_FOUND);
        }
        log.info("get Specialization by id={}", id);
        return convertSpecializationToSpecializationResponce(specializationRepository.getById(id));
    }

    @Override
    public List<SpecializationResponce> getAllSpecialization(int page, int size) {

        Page<Specialization> specializationPage=specializationRepository.findAll(PageRequest.of(page, size));
        List<SpecializationResponce> specializationResponceList = new ArrayList<>();
        specializationPage.forEach(specialization ->
                specializationResponceList.add(convertSpecializationToSpecializationResponce(specialization)));
        log.info("get all Specializations");
        return specializationResponceList;
    }

    @Override
    public String updateSpecialization(SpecializationRequest request) throws ServiceException {
        if (request.getId()==null) {
            throw new ServiceException("Specialization bad request",TypicalError.BAD_REQUEST);
        }
        Optional<Specialization> specialization = specializationRepository.findById(request.getId());
        if (specialization.isEmpty()) {
            throw new ServiceException("Specialization not found", TypicalError.NOT_FOUND);
        }
        Specialization update = specializationRepository.getById(request.getId());
        if (request.getName() != null) {
            update.setName(request.getName());
        }
        specializationRepository.saveAndFlush(update);
        log.info("updated Specialization id={}", request.getId());
        return "updated Specialization  id=" + update.getId() + " name=" + request.getName();

    }

    @Override
    public String deleteSpecialization(Long id) throws ServiceException {
        Optional<Specialization> specialization= specializationRepository.findById(id);
        if (specialization.isEmpty()) {
            throw new ServiceException("Specialization ",TypicalError.NOT_FOUND);
        }
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
        List<String> doctorResponceList = new ArrayList<>();
        List<String> medicalBillResponceList = new ArrayList<>();
        responce.getDoctorList().forEach(doctor ->
                doctorResponceList.add("id = "+doctor.getId()+", name = "+doctor.getName())
        );
        responce.getMedicalBillList().forEach(medicalBill ->
                medicalBillResponceList.add("id = "+medicalBill.getId()+", name = "+medicalBill.getName()));

        return new SpecializationResponce(responce.getId(),
                responce.getName(),
                doctorResponceList,
                medicalBillResponceList);
    }
}
