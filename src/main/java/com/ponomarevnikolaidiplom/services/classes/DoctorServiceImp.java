package com.ponomarevnikolaidiplom.services.classes;

import com.ponomarevnikolaidiplom.dto.request.DoctorRequest;
import com.ponomarevnikolaidiplom.dto.responce.AppointmentResponce;
import com.ponomarevnikolaidiplom.dto.responce.DoctorResponce;
import com.ponomarevnikolaidiplom.entities.District;
import com.ponomarevnikolaidiplom.entities.Doctor;
import com.ponomarevnikolaidiplom.entities.Specialization;
import com.ponomarevnikolaidiplom.repozitories.DistrictRepository;
import com.ponomarevnikolaidiplom.repozitories.DoctorRepository;
import com.ponomarevnikolaidiplom.repozitories.SpecializationRepository;
import com.ponomarevnikolaidiplom.services.interfacies.DoctorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DoctorServiceImp implements DoctorService {

    final DoctorRepository doctorRepository;
    final SpecializationRepository specializationRepository;
    final DistrictRepository districtRepository;

    @Override
    public DoctorResponce saveDoctor(DoctorRequest doctorRequest) {

        log.info("new Doctor added name = "+doctorRequest.getName());
        return convertDoctorToDoctorResponce(doctorRepository.save(convertRequestToDoctor(doctorRequest)));
    }

    @Override
    public DoctorResponce getDoctor(Long id) {
        log.info("get Doctor by id={}", id);
        return convertDoctorToDoctorResponce(doctorRepository.findDoctorById(id));

    }

    @Override
    public List<DoctorResponce> getAllDoctors() {
        log.info("get all Doctors");
        List<DoctorResponce> doctorResponceList=new ArrayList<>();
        doctorRepository.findAll().forEach(doctor ->
            doctorResponceList.add(convertDoctorToDoctorResponce(doctor))
        );
        return doctorResponceList;
    }

    @Override
    public String updateDoctor(DoctorRequest request) {
        Doctor update = doctorRepository.findDoctorById(request.getId());
        if (update == null) {
            throw new RuntimeException("Doctor not found");
        }
        if (request.getName() != null) {
            update.setName(request.getName());
        }

        doctorRepository.saveAndFlush(update);
        log.info("updated Doctor id={} to name={}", request.getId(), request.getName());
        return "Обновлен доктор с id = " + update.getId()+" name = "+request.getName();
    }

    @Override
    public String deleteDoctor(Long id) {
        log.info("deleted Doctor id={} ", id);
        doctorRepository.deleteById(id);
        return "Доктор удален id="+id;
    }

    @Override
    public String addSpecializationToDoctor(Long idSpecialization, Long idDoctor) {
        Doctor doctor=doctorRepository.findDoctorById(idDoctor);
        Specialization specialization=specializationRepository.getById(idSpecialization);
        if(doctor==null){
            throw new RuntimeException("Doctor not found");
        }
        if(specialization==null){
            throw new RuntimeException("Specialization not found");
        }
        doctor.getSpecializationList().add(specialization);
        specialization.getDoctorList().add(doctor);
        doctorRepository.saveAndFlush(doctor);
        specializationRepository.saveAndFlush(specialization);
        log.info("added Specialization name ={} to Doctor name={}", specialization.getName(), doctor.getName());
        return "Specialization name = "+specialization.getName()+" added to Doctor name="+doctor.getName();
    }

    @Override
    public String deleteSpecializationFromDoctor(Long idSpecialization, Long idDoctor) {
        Doctor doctor=doctorRepository.findDoctorById(idDoctor);
        Specialization specialization=specializationRepository.getById(idSpecialization);
        if(doctor==null){
            throw new RuntimeException("Doctor not found");
        }
        if(specialization==null){
            throw new RuntimeException("Specialization not found");
        }
        specialization.getDoctorList().remove(doctor);
        doctor.getSpecializationList().remove(specialization);
        doctorRepository.saveAndFlush(doctor);
        specializationRepository.saveAndFlush(specialization);
        log.info("deleted Specialization name ={} from Doctor name={}", specialization.getName(), doctor.getName());
        return "deleted Specialization name = "+specialization.getName()+" from Doctor name="+doctor.getName();
    }

    @Override
    public String addDistrictToDoctror(Long idDistrict, Long idDoctor) {
        Doctor doctor=doctorRepository.findDoctorById(idDoctor);
        District district=districtRepository.getById(idDistrict);
        if(doctor==null){
            throw new RuntimeException("Doctor not found");
        }
        if(district==null){
            throw new RuntimeException("district not found");
        }
        doctor.setDistrict(district);
        district.setDistrictDoctor(doctor);
        doctorRepository.saveAndFlush(doctor);
        districtRepository.saveAndFlush(district);
        log.info("added District name ={} to Doctor name={}", district.getName(), doctor.getName());
        return "District name = "+district.getName()+" added to Doctor name="+doctor.getName();
    }

    @Override
    public String deleteDistrictToDoctror(Long idDistrict, Long idDoctor) {
        Doctor doctor=doctorRepository.findDoctorById(idDoctor);
        District district=districtRepository.getById(idDistrict);
        if(doctor==null){
            throw new RuntimeException("Doctor not found");
        }
        if(district==null){
            throw new RuntimeException("District not found");
        }
        doctor.setDistrict(null);
        district.setDistrictDoctor(null);
        doctorRepository.saveAndFlush(doctor);
        districtRepository.saveAndFlush(district);
        log.info("deleted District name ={} from Doctor name={}", district.getName(), doctor.getName());
        return "deleted District name = "+district.getName()+" from Doctor name="+doctor.getName();
    }


    private Doctor convertRequestToDoctor(DoctorRequest request) {

        return new Doctor(request.getId(), request.getName(), new ArrayList<>(),null,new ArrayList<>());
    }
    private DoctorResponce convertDoctorToDoctorResponce(Doctor responce) {
        List<String> specializationList=new ArrayList<>();
        List<String> appointmentStringList=new ArrayList<>();
        String distName="not added";
        if (responce.getDistrict()!=null){
            distName=responce.getDistrict().getName();
        }
        responce.getSpecializationList().forEach(specialization ->
            specializationList.add("id = "+specialization.getId()+", name = "+specialization.getName()));
        responce.getAppointmentList().forEach(appointment -> appointmentStringList.add("id="+appointment.getId()
                +", patient id=" +appointment.getPatient().getId()
                +", date="+appointment.getDateAndTimeOfAppointment()));
        return new DoctorResponce(responce.getId(),
                responce.getName(),
                specializationList,
                distName,
                appointmentStringList);
    }

}
