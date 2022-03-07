package com.ponomarevnikolaidiplom.services.interfacies;

import com.ponomarevnikolaidiplom.dto.request.DoctorRequest;
import com.ponomarevnikolaidiplom.entities.Doctor;

import java.util.List;

public interface DoctorService {
    String saveDoctor(DoctorRequest request);
    Doctor getDoctor(Long id);
    List<Doctor> getAllDoctors();
    String updateDoctor(DoctorRequest request);
    void deleteDoctor(Long id);
}
