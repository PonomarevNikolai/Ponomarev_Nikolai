package com.ponomarevnikolaidiplom.controllers;

import com.ponomarevnikolaidiplom.dto.request.AppointmentRequest;
import com.ponomarevnikolaidiplom.dto.responce.AppointmentResponce;
import com.ponomarevnikolaidiplom.services.interfacies.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/appointment")
public class AppointmentController {

    final AppointmentService appointmentService;

    @GetMapping("/all")
    public ResponseEntity<List<AppointmentResponce>> getAllAppointment() {
        return ResponseEntity.ok().body(appointmentService.getAllAppointment());
    }
    @GetMapping("/{id}")
    public ResponseEntity<AppointmentResponce> getAppointment(@PathVariable @RequestBody Long id){
        return ResponseEntity.ok().body(appointmentService.getAppointment(id));
    }
    @PostMapping("/save")
    public ResponseEntity<AppointmentResponce> saveAppointment(@RequestBody AppointmentRequest request){
        return ResponseEntity.ok().body(appointmentService.saveAppointment(request));
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateAppointment(@RequestBody AppointmentRequest request){

        return ResponseEntity.ok().body(appointmentService.updateAppointment(request));
    }

    @DeleteMapping ("/delete")
    public ResponseEntity<String> deleteAppointment(@RequestBody AppointmentRequest request) {
        return ResponseEntity.ok().body(appointmentService.deleteAppointment(request.getId()));
    }
}
