package com.ponomarevnikolaidiplom.services.classes;

import com.ponomarevnikolaidiplom.dto.request.DistrictRequest;
import com.ponomarevnikolaidiplom.dto.responce.DistrictResponce;
import com.ponomarevnikolaidiplom.entities.District;
import com.ponomarevnikolaidiplom.exceptionhandler.TypicalError;
import com.ponomarevnikolaidiplom.exceptions.ServiceException;
import com.ponomarevnikolaidiplom.repozitories.DistrictRepository;
import com.ponomarevnikolaidiplom.services.interfacies.DistrictService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DistrictServiceImp implements DistrictService {

    final DistrictRepository districtRepository;
    private static final  String DISTRICTNOTFOUND = "District not found";

    @Override
    public DistrictResponce saveDistrict(DistrictRequest request) {
        District district=districtRepository.save(convertRequestToDistrict(request));
        log.info("new District added");
        return convertDistrictToDistrictResponce(district);

    }

    @Override
    public DistrictResponce getDistrict(Long id) throws ServiceException {
        Optional<District> districtOptional = districtRepository.findById(id);

        if (districtOptional.isEmpty()) {

            throw new ServiceException(DISTRICTNOTFOUND, TypicalError.NOT_FOUND);
        }
        log.info("get District by id={}", id);
        return convertDistrictToDistrictResponce(districtRepository.getById(id));
    }

    @Override
    public List<DistrictResponce> getAllDistricts(int page, int size) {
        Page<District> districtPage=districtRepository.findAll(PageRequest.of(page, size));
        List<DistrictResponce> districtResponceList = new ArrayList<>();
        districtRepository.findAll().forEach(district ->
                districtResponceList.add(convertDistrictToDistrictResponce(district)));
        log.info("get all Districts");
        return districtResponceList;
    }

    @Override
    public String updateDistrict(DistrictRequest request) throws ServiceException {

        Optional<District> districtOptional = districtRepository.findById(request.getId());

        if (districtOptional.isEmpty()) {
            throw new ServiceException(DISTRICTNOTFOUND, TypicalError.NOT_FOUND);
        }
        District update = districtRepository.getById(request.getId());

        if (request.getName() == null) {
            throw new ServiceException("Nothing change", TypicalError.BAD_REQUEST);
        }else{
            update.setName(request.getName());
        }
        districtRepository.saveAndFlush(update);
        log.info("updated District id={}", request.getId());
        return "updated District  id=" + update.getId() + " name=" + request.getName();
    }

    @Override
    public String deleteDistrict(Long id) throws ServiceException {
        Optional<District> districtOptional = districtRepository.findById(id);

        if (districtOptional.isEmpty()) {
            throw new ServiceException(DISTRICTNOTFOUND, TypicalError.NOT_FOUND);
        }
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
