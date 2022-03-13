package com.ponomarevnikolaidiplom.dto.request;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class AppointmentRequest {
    private  Long id;
    private Long idPatient;
    private Long idDoctor;
    private String dateAndTimeOfAppointment;
}
