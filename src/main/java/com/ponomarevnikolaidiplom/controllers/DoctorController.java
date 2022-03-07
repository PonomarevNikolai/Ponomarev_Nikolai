package com.ponomarevnikolaidiplom.controllers;

import com.ponomarevnikolaidiplom.dto.request.DoctorRequest;
import com.ponomarevnikolaidiplom.entities.Doctor;
import com.ponomarevnikolaidiplom.services.interfacies.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/doctor")
public class DoctorController {

    final DoctorService doctorService;

    @GetMapping("/all")
    public ResponseEntity<List<Doctor>> getAllDoctors() {
        return ResponseEntity.ok().body(doctorService.getAllDoctors());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Doctor> getDoctor(@PathVariable @RequestBody Long id){
        return ResponseEntity.ok().body(doctorService.getDoctor(id));
    }
    @PostMapping("/save")
    public ResponseEntity<String> saveDoctor(@RequestBody DoctorRequest request){
        return ResponseEntity.ok().body(doctorService.saveDoctor(request));
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateDoctor(@RequestBody DoctorRequest request){
        doctorService.updateDoctor(request);
        return ResponseEntity.ok().body(doctorService.updateDoctor(request));
    }

    @DeleteMapping ("/delete")
    public ResponseEntity<String> deleteDoctor(@RequestBody DoctorRequest request) {
        doctorService.deleteDoctor(request.getId());
        return ResponseEntity.ok().body("Доктор удален id="+request.getId());
    }
}

