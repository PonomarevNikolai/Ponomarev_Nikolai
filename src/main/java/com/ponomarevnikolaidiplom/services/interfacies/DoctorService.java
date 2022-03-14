package com.ponomarevnikolaidiplom.services.interfacies;

import com.ponomarevnikolaidiplom.dto.request.DoctorRequest;
import com.ponomarevnikolaidiplom.dto.responce.DoctorResponce;
import com.ponomarevnikolaidiplom.exceptions.ServiceException;

import java.util.List;

public interface DoctorService {
    DoctorResponce saveDoctor(DoctorRequest request);

    DoctorResponce getDoctor(Long id) throws ServiceException;

    List<DoctorResponce> getAllDoctors();

    String updateDoctor(DoctorRequest request) throws ServiceException;

    String deleteDoctor(Long id) throws ServiceException;

    String addSpecializationToDoctor(Long idSpecialization, Long idDoctor) throws ServiceException;

    String deleteSpecializationFromDoctor(Long idSpecialization, Long idDoctor) throws ServiceException;

    String addDistrictToDoctror(Long idDistrict, Long idDoctor) throws ServiceException;

    String deleteDistrictToDoctror(Long idDistrict, Long idDoctor) throws ServiceException;
}
