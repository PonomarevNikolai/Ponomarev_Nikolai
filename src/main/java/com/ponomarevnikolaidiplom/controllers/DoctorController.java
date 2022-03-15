package com.ponomarevnikolaidiplom.controllers;

import com.ponomarevnikolaidiplom.dto.DtoId;
import com.ponomarevnikolaidiplom.dto.request.DoctorRequest;
import com.ponomarevnikolaidiplom.dto.responce.DoctorResponce;
import com.ponomarevnikolaidiplom.exceptions.ServiceException;
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
    public ResponseEntity<List<DoctorResponce>> getAllDoctors(
            @RequestParam(value = "page",required = false,defaultValue = "0") Integer page,
            @RequestParam(value = "size",required = false,defaultValue = "10") Integer size
    ) {
        return ResponseEntity.ok().body(doctorService.getAllDoctors(page,size));
    }
    @GetMapping("/{id}")
    public ResponseEntity<DoctorResponce> getDoctor(@PathVariable @RequestBody Long id) throws ServiceException {
        return ResponseEntity.ok().body(doctorService.getDoctor(id));
    }
    @PostMapping("/save")
    public ResponseEntity<DoctorResponce> saveDoctor(@RequestBody DoctorRequest request){
        return ResponseEntity.ok().body(doctorService.saveDoctor(request));
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateDoctor(@RequestBody DoctorRequest request) throws ServiceException {
        doctorService.updateDoctor(request);
        return ResponseEntity.ok().body(doctorService.updateDoctor(request));
    }

    @DeleteMapping ("/delete")
    public ResponseEntity<String> deleteDoctor(@RequestBody DoctorRequest request) throws ServiceException {
        return ResponseEntity.ok().body(doctorService.deleteDoctor(request.getId()));
    }
    @PostMapping("/addspecialization")
    public ResponseEntity<String> addSpecializationToDoctor(@RequestBody DtoId dtoId) throws ServiceException {
        return ResponseEntity.ok().body(doctorService.addSpecializationToDoctor(dtoId.getIdSpecilization(), dtoId.getIdDoctor()));
    }
    @DeleteMapping("/deletespecialization")
    public ResponseEntity<String> deleteSpecializationFromDoctor(@RequestBody DtoId dtoId) throws ServiceException {
        return ResponseEntity.ok().body(doctorService.deleteSpecializationFromDoctor(dtoId.getIdSpecilization(), dtoId.getIdDoctor()));
    }

    @PostMapping("/adddistrict")
    public ResponseEntity<String> addDistrictToDoctor(@RequestBody DtoId dtoId) throws ServiceException {
        return ResponseEntity.ok().body(doctorService.addDistrictToDoctror(dtoId.getIdDistrict(), dtoId.getIdDoctor()));
    }
    @DeleteMapping("/deletedistrict")
    public ResponseEntity<String> deleteDistrictFromDoctor(@RequestBody DtoId dtoId) throws ServiceException {
        return ResponseEntity.ok().body(doctorService.deleteDistrictToDoctror(dtoId.getIdDistrict(), dtoId.getIdDoctor()));
    }
}

