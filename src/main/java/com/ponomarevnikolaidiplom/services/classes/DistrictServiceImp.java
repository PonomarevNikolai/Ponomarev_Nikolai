package com.ponomarevnikolaidiplom.services.classes;

import com.ponomarevnikolaidiplom.dto.request.DistrictRequest;
import com.ponomarevnikolaidiplom.dto.responce.DistrictResponce;
import com.ponomarevnikolaidiplom.entities.District;
import com.ponomarevnikolaidiplom.repozitories.DistrictRepository;
import com.ponomarevnikolaidiplom.services.interfacies.DistrictService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DistrictServiceImp implements DistrictService {

    final DistrictRepository districtRepository;

    @Override
    public DistrictResponce saveDistrict(DistrictRequest request) {
        District district=districtRepository.save(convertRequestToDistrict(request));
        log.info("new District added");
        return convertDistrictToDistrictResponce(district);

    }

    @Override
    public DistrictResponce getDistrict(Long id) {
        log.info("get District by id={}", id);
        return convertDistrictToDistrictResponce(districtRepository.getById(id));
    }

    @Override
    public List<DistrictResponce> getAllDistricts() {
        log.info("get all Districts");
        List<DistrictResponce> districtResponceList = new ArrayList<>();
        districtRepository.findAll().forEach(district ->
                districtResponceList.add(convertDistrictToDistrictResponce(district)));
        return districtResponceList;
    }

    @Override
    public String updateDistrict(DistrictRequest request) {
        District update = districtRepository.getById(request.getId());
        if (update == null) {
            throw new RuntimeException("District not found");
        }
        if (request.getName() != null) {
            update.setName(request.getName());
        }
        districtRepository.saveAndFlush(update);
        log.info("updated District id={}", request.getId());
        return "updated District  id=" + update.getId() + " name=" + request.getName();
    }

    @Override
    public String deleteDistrict(Long id) {
        log.info("deleted District id={}", id);
        districtRepository.deleteById(id);
        return "deleted District id=" + id;
    }

    private District convertRequestToDistrict(DistrictRequest request) {

        return new District(request.getId(),
                request.getName(), null);
    }

    private DistrictResponce convertDistrictToDistrictResponce(District responce) {
        String doctorName="Not assigned";
        if(responce.getDistrictDoctor()!=null){
            doctorName=responce.getDistrictDoctor().getName();
        }
        return new DistrictResponce(responce.getId(),
                responce.getName(),
               doctorName );
    }
}
