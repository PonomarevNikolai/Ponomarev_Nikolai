package com.ponomarevnikolaidiplom.controllers;

import com.ponomarevnikolaidiplom.dto.request.PatientRequest;
import com.ponomarevnikolaidiplom.entities.Patient;
import com.ponomarevnikolaidiplom.service.interfacies.PatientService;
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
    public ResponseEntity<List<Patient>> getAllPatients() {
        return ResponseEntity.ok().body(patientService.getAllPatients());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatient(@PathVariable @RequestBody Long id){
        return ResponseEntity.ok().body(patientService.getPatient(id));
    }
    @PostMapping("/save")
    public ResponseEntity<String> savePatient(@RequestBody PatientRequest request){
        return ResponseEntity.ok().body(patientService.savePatient(request));
    }

    @PutMapping("/update")
    public ResponseEntity<String> updatePatient(@RequestBody PatientRequest request){
        patientService.updatePatient(request);
        return ResponseEntity.ok().body("Обновлен Пациент с id={}"+request.getId());
    }

    @DeleteMapping ("/delete")
    public ResponseEntity<String> deletePatient(@RequestBody PatientRequest request) {
        patientService.deletePatient(request.getId());
        return ResponseEntity.ok().body("удален Пациент с id="+request.getId());
    }
}
