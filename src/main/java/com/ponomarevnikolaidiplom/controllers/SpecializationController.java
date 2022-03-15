package com.ponomarevnikolaidiplom.controllers;

import com.ponomarevnikolaidiplom.dto.request.SpecializationRequest;
import com.ponomarevnikolaidiplom.dto.responce.SpecializationResponce;
import com.ponomarevnikolaidiplom.exceptions.ServiceException;
import com.ponomarevnikolaidiplom.services.interfacies.SpecializationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/specialization")
public class SpecializationController {

    final SpecializationService specializationService;

    @GetMapping("/all")
    public ResponseEntity<List<SpecializationResponce>> getAllSpecialization(
            @RequestParam(value = "page",required = false,defaultValue = "0") Integer page,
            @RequestParam(value = "size",required = false,defaultValue = "10") Integer size) {
        return ResponseEntity.ok().body(specializationService.getAllSpecialization(page,size));
    }
    @GetMapping("/{id}")
    public ResponseEntity<SpecializationResponce> getSpecialization(@PathVariable @RequestBody Long id) throws ServiceException {
        return ResponseEntity.ok().body(specializationService.getSpecialization(id));
    }
    @PostMapping("/save")
    public ResponseEntity<SpecializationResponce> saveSpecialization(@RequestBody SpecializationRequest request){
        return ResponseEntity.ok().body(specializationService.saveSpecialization(request));
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateSpecialization(@RequestBody SpecializationRequest request) throws ServiceException {

        return ResponseEntity.ok().body(specializationService.updateSpecialization(request));
    }

    @DeleteMapping ("/delete")
    public ResponseEntity<String> deleteSpecialization(@RequestBody SpecializationRequest request) throws ServiceException {
        return ResponseEntity.ok().body(specializationService.deleteSpecialization(request.getId()));
    }
}
