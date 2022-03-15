package com.ponomarevnikolaidiplom.services.classes;

import com.ponomarevnikolaidiplom.dto.request.PatientRequest;
import com.ponomarevnikolaidiplom.dto.responce.PatientResponce;
import com.ponomarevnikolaidiplom.entities.Patient;
import com.ponomarevnikolaidiplom.exceptionhandler.TypicalError;
import com.ponomarevnikolaidiplom.exceptions.ServiceException;
import com.ponomarevnikolaidiplom.repozitories.PatientRepository;
import com.ponomarevnikolaidiplom.services.interfacies.PatientService;
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
public class PatientServiceImp implements PatientService {

    final PatientRepository patientRepository;
    private static final String PATIENTNOTFOUND = "Patient not found";

    public PatientResponce savePatient(PatientRequest request) throws ServiceException {
        if (request.getNumberOfInsurance() == null) {
            throw new ServiceException("Patient number of insurance not fill", TypicalError.BAD_REQUEST);
        }
        if (request.getName() == null) {
            throw new ServiceException("Patient name not fill", TypicalError.BAD_REQUEST);
        }
        log.info("new Patient added name={}", request.getName());
        return convertPatientToPatientResponce(patientRepository.save(convertRequestToPatient(request)));
    }

    @Override
    public PatientResponce getPatient(Long id) throws ServiceException {
        Optional<Patient> patient = patientRepository.findById(id);
        if (patient.isEmpty()) {

            throw new ServiceException(PATIENTNOTFOUND, TypicalError.NOT_FOUND);
        }
        log.info("get Patient by id");
        return convertPatientToPatientResponce(patientRepository.getById(id));
    }

    @Override
    public List<PatientResponce> getAllPatients(int page, int size) {
        Page<Patient> patientPage=patientRepository.findAll(PageRequest.of(page,size));
        log.info("get all Patients ");
        List<PatientResponce> patientResponceList = new ArrayList<>();
        patientRepository.findAll().forEach(patient -> patientResponceList.add(convertPatientToPatientResponce(patient)));
        return patientResponceList;
    }

    @Override
    public String updatePatient(PatientRequest request) throws ServiceException {
        Optional<Patient> patient = patientRepository.findById(request.getId());
        if (patient.isEmpty()) {
            throw new ServiceException(PATIENTNOTFOUND, TypicalError.NOT_FOUND);
        }
        Patient update = patientRepository.getById(request.getId());
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
        return "Обновлен Pacient с id=" + update.getId() + " name=" + request.getName();

    }

    @Override
    public void deletePatient(Long id) throws ServiceException {
        Optional<Patient> patient = patientRepository.findById(id);
        if (patient.isEmpty()) {
            throw new ServiceException(PATIENTNOTFOUND, TypicalError.NOT_FOUND);
        }
        log.info("deleted Patient id={}", id);
        patientRepository.deleteById(id);

    }

    private Patient convertRequestToPatient(PatientRequest request) {

        return new Patient(request.getId(),
                request.getNumberOfInsurance(),
                request.getName(),
                request.getPhoneNumber(),
                request.getAddress(),
                new ArrayList<>());
    }

    private PatientResponce convertPatientToPatientResponce(Patient responce) {
        List<String> appointmentStringList = new ArrayList<>();
        responce.getAppointmentList().forEach(appointment -> appointmentStringList.add("id=" + appointment.getId()
                + ", patient id=" + appointment.getPatient().getId()
                + ", date=" + appointment.getDateAndTimeOfAppointment()));
        return new PatientResponce(responce.getId(),
                responce.getNumberOfInsurance(),
                responce.getName(),
                responce.getPhoneNumber(),
                responce.getAddress(),
                appointmentStringList);
    }
}
