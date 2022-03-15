package com.ponomarevnikolaidiplom.services.interfacies;



import com.ponomarevnikolaidiplom.dto.request.AppointmentRequest;
import com.ponomarevnikolaidiplom.dto.responce.AppointmentResponce;
import com.ponomarevnikolaidiplom.exceptions.ServiceException;

import java.util.List;

public interface AppointmentService {
    AppointmentResponce saveAppointment(AppointmentRequest request) throws ServiceException;

    AppointmentResponce getAppointment(Long id) throws ServiceException;

    List<AppointmentResponce> getAllAppointment(int page, int size);

    String updateAppointment(AppointmentRequest request) throws ServiceException;

    String deleteAppointment(Long id) throws ServiceException;
}
