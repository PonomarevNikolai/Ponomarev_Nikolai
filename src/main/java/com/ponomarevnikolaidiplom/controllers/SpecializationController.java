package com.ponomarevnikolaidiplom.controllers;

import com.ponomarevnikolaidiplom.dto.request.SpecializationRequest;
import com.ponomarevnikolaidiplom.dto.responce.SpecializationResponce;
import com.ponomarevnikolaidiplom.entities.Specialization;
import com.ponomarevnikolaidiplom.services.interfacies.SpecializationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SpecializationController {

    final SpecializationService specializationService;

    @GetMapping("/all")
    public ResponseEntity<List<SpecializationResponce>> getAllSpecialization() {
        return ResponseEntity.ok().body(specializationService.getAllSpecialization());
    }
    @GetMapping("/{id}")
    public ResponseEntity<SpecializationResponce> getSpecialization(@PathVariable @RequestBody Long id){
        return ResponseEntity.ok().body(specializationService.getSpecialization(id));
    }
    @PostMapping("/save")
    public ResponseEntity<Specialization> saveSpecialization(@RequestBody SpecializationRequest request){
        return ResponseEntity.ok().body(specializationService.saveSpecialization(request));
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateSpecialization(@RequestBody SpecializationRequest request){

        return ResponseEntity.ok().body(specializationService.updateSpecialization(request));
    }

    @DeleteMapping ("/delete")
    public ResponseEntity<String> deleteSpecialization(@RequestBody SpecializationRequest request) {
        return ResponseEntity.ok().body(specializationService.deleteSpecialization(request.getId()));
    }
}
