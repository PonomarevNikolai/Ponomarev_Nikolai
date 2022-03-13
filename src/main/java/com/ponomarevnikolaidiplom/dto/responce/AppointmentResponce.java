package com.ponomarevnikolaidiplom.dto.responce;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AppointmentResponce {
    private  Long id;
    private String namePatient;
    private String nameDoctor;
    private String dateAndTimeOfAppointment;
}
