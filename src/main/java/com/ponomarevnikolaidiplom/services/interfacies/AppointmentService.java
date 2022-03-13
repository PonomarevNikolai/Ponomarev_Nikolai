package com.ponomarevnikolaidiplom.services.interfacies;



import com.ponomarevnikolaidiplom.dto.request.AppointmentRequest;
import com.ponomarevnikolaidiplom.dto.responce.AppointmentResponce;

import java.util.List;

public interface AppointmentService {
    AppointmentResponce saveAppointment(AppointmentRequest request);

    AppointmentResponce getAppointment(Long id);

    List<AppointmentResponce> getAllAppointment();

    String updateAppointment(AppointmentRequest request);

    String deleteAppointment(Long id);
}
