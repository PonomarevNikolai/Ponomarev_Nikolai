package com.ponomarevnikolaidiplom.service.interfacies;

import com.ponomarevnikolaidiplom.dto.request.DoctorRequest;
import com.ponomarevnikolaidiplom.entities.Doctor;

import java.util.List;

public interface DoctorService {
    String saveDoctor(DoctorRequest request);
    Doctor getDoctor(Long id);
    List<Doctor> getAllDoctors();
    void updateDoctor(DoctorRequest request);
    void deleteDoctor(Long id);
}
