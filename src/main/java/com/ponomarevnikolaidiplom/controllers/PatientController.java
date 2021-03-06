package com.ponomarevnikolaidiplom.controllers;

import com.ponomarevnikolaidiplom.dto.request.PatientRequest;
import com.ponomarevnikolaidiplom.dto.responce.PatientResponce;
import com.ponomarevnikolaidiplom.exceptions.ServiceException;
import com.ponomarevnikolaidiplom.services.interfacies.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/patient")
public class PatientController {

    final PatientService patientService;

    @GetMapping("/all")
    public ResponseEntity<List<PatientResponce>> getAllPatients(
            @RequestParam(value = "page",required = false,defaultValue = "0") Integer page,
            @RequestParam(value = "size",required = false,defaultValue = "10") Integer size
    ) {
        return ResponseEntity.ok().body(patientService.getAllPatients(page,size));
    }
    @GetMapping("/{id}")
    public ResponseEntity<PatientResponce> getPatient(@PathVariable @RequestBody Long id) throws ServiceException {
        return ResponseEntity.ok().body(patientService.getPatient(id));
    }
    @PostMapping("/save")
    public ResponseEntity<PatientResponce> savePatient(@RequestBody PatientRequest request) throws ServiceException {
        return ResponseEntity.ok().body(patientService.savePatient(request));
    }

    @PutMapping("/update")
    public ResponseEntity<String> updatePatient(@RequestBody PatientRequest request) throws ServiceException {

        return ResponseEntity.ok().body(patientService.updatePatient(request));
    }

    @DeleteMapping ("/delete")
    public ResponseEntity<String> deletePatient(@RequestBody PatientRequest request) throws ServiceException {
        patientService.deletePatient(request.getId());
        return ResponseEntity.ok().body("удален Пациент с id="+request.getId());
    }
}
