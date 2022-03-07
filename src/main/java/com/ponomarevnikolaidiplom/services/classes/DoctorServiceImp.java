package com.ponomarevnikolaidiplom.services.classes;

import com.ponomarevnikolaidiplom.dto.request.DoctorRequest;
import com.ponomarevnikolaidiplom.entities.Doctor;
import com.ponomarevnikolaidiplom.repozitories.DoctorRepository;
import com.ponomarevnikolaidiplom.services.interfacies.DoctorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DoctorServiceImp implements DoctorService {

    final DoctorRepository doctorRepository;

    @Override
    public String saveDoctor(DoctorRequest doctorRequest) {
        doctorRepository.save(convertRequestToDoctor(doctorRequest));
        log.info("new Doctor added");
        return doctorRequest.getName();
    }

    @Override
    public Doctor getDoctor(Long id) {
        log.info("get Doctor by id={}", id);
        return doctorRepository.findDoctorById(id);

    }

    @Override
    public List<Doctor> getAllDoctors() {
        log.info("get all Doctors");
        return doctorRepository.findAll();
    }

    @Override
    public String updateDoctor(DoctorRequest request) {
        Doctor update = doctorRepository.findDoctorById(request.getId());
        if (update == null) {
            throw new RuntimeException("Doctor not found");
        }
        if (request.getName() != null) {
            update.setName(request.getName());
        }

        doctorRepository.saveAndFlush(update);
        log.info("updated Doctor id={} to name={}", request.getId(), request.getName());
        return "Обновлен доктор с id=" + update.getId()+" name="+request.getName();
    }

    @Override
    public void deleteDoctor(Long id) {
        log.info("deleted Doctor id={} ", id);
        doctorRepository.deleteById(id);

    }

    private Doctor convertRequestToDoctor(DoctorRequest request) {

        return new Doctor(request.getId(), request.getName(), new ArrayList<>());
    }

}
