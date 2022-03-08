package com.ponomarevnikolaidiplom.controllers;

import com.ponomarevnikolaidiplom.dto.DtoId;
import com.ponomarevnikolaidiplom.dto.request.MedicalBillRequest;
import com.ponomarevnikolaidiplom.dto.responce.MedicalBillResponce;
import com.ponomarevnikolaidiplom.services.interfacies.MedicalBillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/medicalbill")
public class MedicalBillController {

   final MedicalBillService medicalBillService;


    @GetMapping("/all")
    public ResponseEntity<List<MedicalBillResponce>> getAllMedicalBills() {
        return ResponseEntity.ok().body(medicalBillService.getAllMedicalBills());
    }
    @GetMapping("/{id}")
    public ResponseEntity<MedicalBillResponce> getMedicalBill(@PathVariable @RequestBody Long id){
        return ResponseEntity.ok().body(medicalBillService.getMedicalBill(id));
    }
    @PostMapping("/save")
    public ResponseEntity<MedicalBillResponce> saveMedicalBill(@RequestBody MedicalBillRequest request){
        return ResponseEntity.ok().body(medicalBillService.saveMedicalBill(request));
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateMedicalBill(@RequestBody MedicalBillRequest request){

        return ResponseEntity.ok().body(medicalBillService.updateMedicalBill(request));
    }

    @DeleteMapping ("/delete")
    public ResponseEntity<String> deleteMedicalBill(@RequestBody MedicalBillRequest request) {

        return ResponseEntity.ok().body(medicalBillService.deleteMedicalBill(request.getId()));
    }
    @PostMapping("/addspecialization")
    public ResponseEntity<String> addSpecializationToMedicalBill(@RequestBody DtoId dtoId){
        return ResponseEntity.ok().body(medicalBillService.addSpecializationToMedicalBill(dtoId.getIdSpecilization(), dtoId.getIdMedicalBill()));
    }
    @DeleteMapping("/deletespecialization")
    public ResponseEntity<String> deleteSpecializationFromMedicalBill(@RequestBody DtoId dtoId){
        return ResponseEntity.ok().body(medicalBillService.deleteSpecializationFromMedicalBill(dtoId.getIdSpecilization(), dtoId.getIdMedicalBill()));
    }

}
