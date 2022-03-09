package com.ponomarevnikolaidiplom.services.interfacies;

import com.ponomarevnikolaidiplom.dto.request.DoctorRequest;
import com.ponomarevnikolaidiplom.dto.responce.DoctorResponce;

import java.util.List;

public interface DoctorService {
    DoctorResponce saveDoctor(DoctorRequest request);

    DoctorResponce getDoctor(Long id);

    List<DoctorResponce> getAllDoctors();

    String updateDoctor(DoctorRequest request);

    String deleteDoctor(Long id);

    String addSpecializationToDoctor(Long idSpecialization, Long idDoctor);

    String deleteSpecializationFromDoctor(Long idSpecialization, Long idDoctor);

    String addDistrictToDoctror(Long idDistrict, Long idDoctor);

    String deleteDistrictToDoctror(Long idDistrict, Long idDoctor);
}
