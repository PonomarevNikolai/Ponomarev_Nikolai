package com.ponomarevnikolaidiplom.controllers;

import com.ponomarevnikolaidiplom.dto.request.AppointmentRequest;
import com.ponomarevnikolaidiplom.dto.responce.AppointmentResponce;
import com.ponomarevnikolaidiplom.exceptions.ServiceException;
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
    public ResponseEntity<List<AppointmentResponce>> getAllAppointment(
            @RequestParam(value = "page",required = false,defaultValue = "0") Integer page,
            @RequestParam(value = "size",required = false,defaultValue = "10") Integer size) {
        return ResponseEntity.ok().body(appointmentService.getAllAppointment(page,size));
    }
    @GetMapping("/{id}")
    public ResponseEntity<AppointmentResponce> getAppointment(@PathVariable @RequestBody Long id) throws ServiceException {
        return ResponseEntity.ok().body(appointmentService.getAppointment(id));
    }
    @PostMapping("/save")
    public ResponseEntity<AppointmentResponce> saveAppointment(@RequestBody AppointmentRequest request) throws ServiceException {
        return ResponseEntity.ok().body(appointmentService.saveAppointment(request));
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateAppointment(@RequestBody AppointmentRequest request) throws ServiceException {

        return ResponseEntity.ok().body(appointmentService.updateAppointment(request));
    }

    @DeleteMapping ("/delete")
    public ResponseEntity<String> deleteAppointment(@RequestBody AppointmentRequest request) throws ServiceException {
        return ResponseEntity.ok().body(appointmentService.deleteAppointment(request.getId()));
    }
}
