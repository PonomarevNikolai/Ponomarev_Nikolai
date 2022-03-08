package com.ponomarevnikolaidiplom.controllers;

import com.ponomarevnikolaidiplom.dto.DtoId;
import com.ponomarevnikolaidiplom.dto.request.DoctorRequest;
import com.ponomarevnikolaidiplom.dto.responce.DoctorResponce;
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
    public ResponseEntity<List<DoctorResponce>> getAllDoctors() {
        return ResponseEntity.ok().body(doctorService.getAllDoctors());
    }
    @GetMapping("/{id}")
    public ResponseEntity<DoctorResponce> getDoctor(@PathVariable @RequestBody Long id){
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
        return ResponseEntity.ok().body(doctorService.deleteDoctor(request.getId()));
    }
    @PostMapping("/add-specialization")
    public ResponseEntity<String> addSpecializationToDoctor(@RequestBody DtoId dtoId){
        return ResponseEntity.ok().body(doctorService.addSpecializationToDoctor(dtoId.getIdSpecilization(), dtoId.getIdDoctor()));
    }
    @DeleteMapping("/delete-specialization")
    public ResponseEntity<String> deleteSpecializationFromDoctor(@RequestBody DtoId dtoId){
        return ResponseEntity.ok().body(doctorService.deleteSpecializationFromDoctor(dtoId.getIdSpecilization(), dtoId.getIdDoctor()));
    }
}

