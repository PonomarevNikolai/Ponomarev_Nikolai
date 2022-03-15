package com.ponomarevnikolaidiplom.dto.request;

import lombok.Data;

@Data
public class AppointmentRequest {
    private  Long id;
    private Long idPatient;
    private Long idDoctor;
    private String dateAndTimeOfAppointment;
}
