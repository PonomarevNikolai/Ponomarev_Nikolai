package com.ponomarevnikolaidiplom.controllers;

import com.ponomarevnikolaidiplom.dto.request.DistrictRequest;
import com.ponomarevnikolaidiplom.dto.responce.DistrictResponce;
import com.ponomarevnikolaidiplom.services.interfacies.DistrictService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/district")
public class DistrictController {

final DistrictService districtService;

    @GetMapping("/all")
    public ResponseEntity<List<DistrictResponce>> getAllDistrict() {
        return ResponseEntity.ok().body(districtService.getAllDistricts());
    }
    @GetMapping("/{id}")
    public ResponseEntity<DistrictResponce> getDistrict(@PathVariable @RequestBody Long id){
        return ResponseEntity.ok().body(districtService.getDistrict(id));
    }
    @PostMapping("/save")
    public ResponseEntity<DistrictResponce> saveDistrict(@RequestBody DistrictRequest request){
        return ResponseEntity.ok().body(districtService.saveDistrict(request));
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateDistrict(@RequestBody DistrictRequest request){

        return ResponseEntity.ok().body(districtService.updateDistrict(request));
    }

    @DeleteMapping ("/delete")
    public ResponseEntity<String> deleteDistrict(@RequestBody DistrictRequest request) {
        return ResponseEntity.ok().body(districtService.deleteDistrict(request.getId()));
    }
}
