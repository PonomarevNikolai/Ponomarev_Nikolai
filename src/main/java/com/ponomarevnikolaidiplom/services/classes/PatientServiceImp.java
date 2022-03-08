package com.ponomarevnikolaidiplom.services.classes;

import com.ponomarevnikolaidiplom.dto.request.PatientRequest;
import com.ponomarevnikolaidiplom.dto.responce.PatientResponce;
import com.ponomarevnikolaidiplom.entities.Patient;
import com.ponomarevnikolaidiplom.repozitories.PatientRepository;
import com.ponomarevnikolaidiplom.services.interfacies.PatientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PatientServiceImp implements PatientService {

    final PatientRepository patientRepository;

    public PatientResponce savePatient(PatientRequest request) {
        log.info("new Patient added name={}",request.getName());
        return convertPatientToPatientResponce(patientRepository.save(convertRequestToPatient(request)));
    }

    @Override
    public PatientResponce getPatient(Long id) {
        log.info("get Patient by id");
        return convertPatientToPatientResponce(patientRepository.getById(id));
    }

    @Override
    public List<PatientResponce> getAllPatients() {
        log.info("get all Patients ");
        List<PatientResponce> patientResponceList=new ArrayList<>();
        patientRepository.findAll().forEach(patient -> patientResponceList.add(convertPatientToPatientResponce(patient)));
        return patientResponceList;
    }

    @Override
    public String updatePatient(PatientRequest request) {
        Patient update = patientRepository.getById(request.getId());
        if (update == null) {
            throw new RuntimeException("Patient not found");
        }
        if (request.getName() != null) {
            update.setName(request.getName());
        }
        if (request.getNumberOfInsurance() != null) {
            update.setNumberOfInsurance(request.getNumberOfInsurance());
        }

        if (request.getAddress() != null) {
            update.setAddress(request.getAddress());
        }

        if (request.getPhoneNumber() != null) {
            update.setPhoneNumber(request.getPhoneNumber());
        }

        patientRepository.saveAndFlush(update);
        log.info("updated Patient id={}", request.getId());
        return "Обновлен Pacient с id=" + update.getId()+" name="+request.getName();

    }

    @Override
    public void deletePatient(Long id) {
        log.info("deleted Patient id={}", id);
        patientRepository.deleteById(id);

    }

    private Patient convertRequestToPatient(PatientRequest request) {

        return new Patient(request.getId(),
                request.getNumberOfInsurance(),
                request.getName(),
                request.getPhoneNumber(),
                request.getAddress());
    }
    private PatientResponce convertPatientToPatientResponce(Patient responce) {

        return new PatientResponce(responce.getId(),
                responce.getNumberOfInsurance(),
                responce.getName(),
                responce.getPhoneNumber(),
                responce.getAddress());
    }
}
