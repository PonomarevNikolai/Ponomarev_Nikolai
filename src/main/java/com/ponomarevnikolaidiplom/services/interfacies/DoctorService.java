package com.ponomarevnikolaidiplom.services.interfacies;

import com.ponomarevnikolaidiplom.dto.request.DoctorRequest;
import com.ponomarevnikolaidiplom.dto.responce.DoctorResponce;
import com.ponomarevnikolaidiplom.entities.Doctor;

import java.util.List;

public interface DoctorService {
    String saveDoctor(DoctorRequest request);

    DoctorResponce getDoctor(Long id);

    List<DoctorResponce> getAllDoctors();

    String updateDoctor(DoctorRequest request);

    String deleteDoctor(Long id);

    String addSpecializationToDoctor(Long idSpecialization, Long idDoctor);

    String deleteSpecializationFromDoctor(Long idSpecialization, Long idDoctor);
}
