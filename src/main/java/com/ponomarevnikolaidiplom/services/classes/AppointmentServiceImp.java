package com.ponomarevnikolaidiplom.services.classes;

import com.ponomarevnikolaidiplom.dto.request.AppointmentRequest;
import com.ponomarevnikolaidiplom.dto.responce.AppointmentResponce;
import com.ponomarevnikolaidiplom.entities.Appointment;
import com.ponomarevnikolaidiplom.entities.Doctor;
import com.ponomarevnikolaidiplom.entities.Patient;
import com.ponomarevnikolaidiplom.repozitories.AppointmentRepository;
import com.ponomarevnikolaidiplom.repozitories.DoctorRepository;
import com.ponomarevnikolaidiplom.repozitories.PatientRepository;
import com.ponomarevnikolaidiplom.services.interfacies.AppointmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppointmentServiceImp implements AppointmentService {

    final AppointmentRepository appointmentRepository;
    final PatientRepository patientRepository;
    final DoctorRepository doctorRepository;
    private static final String pattern = "dd.MM.yyyy hh:mm";

    @Override
    public AppointmentResponce saveAppointment(AppointmentRequest request) {
        Appointment appointment = appointmentRepository.save(convertRequestToAppointment(request));
        Date dateOfAppointment = appointment.getDateAndTimeOfAppointment();
        Doctor doctor = doctorRepository.findDoctorById(request.getIdDoctor());
        Patient patient = patientRepository.getById(request.getIdPatient());
        doctor.getAppointmentList().forEach(appointment1 -> {
            if (appointment1.getDateAndTimeOfAppointment().compareTo(dateOfAppointment) == 0) {
                throw new RuntimeException("This doctor appointment time closed");
            }
        });
        patient.getAppointmentList().forEach(appointment1 -> {
            if (appointment1.getDateAndTimeOfAppointment().compareTo(dateOfAppointment) == 0) {
                throw new RuntimeException("This patient appointment time closed");
            }
        });
        doctor.getAppointmentList().add(appointment);
        patient.getAppointmentList().add(appointment);
        doctorRepository.saveAndFlush(doctor);
        patientRepository.saveAndFlush(patient);
        return convertAppointmentToResponce(appointment);
    }

    @Override
    public AppointmentResponce getAppointment(Long id) {
        Appointment appointment = appointmentRepository.getById(id);
        if (appointment == null) {
            throw new RuntimeException("Appointment not found");
        }
        return convertAppointmentToResponce(appointment);
    }

    @Override
    public List<AppointmentResponce> getAllAppointment() {
        List<AppointmentResponce> appointmentResponceList = new ArrayList<>();
        appointmentRepository.findAll().forEach(appointment -> appointmentResponceList.add(convertAppointmentToResponce(appointment)));
        return appointmentResponceList;
    }

    @Override
    public String updateAppointment(AppointmentRequest request) {
        Appointment update = appointmentRepository.getById(request.getId());
        Patient patient = patientRepository.getById(request.getIdPatient());
        Doctor doctor = doctorRepository.findDoctorById(request.getIdDoctor());
        Date dateAndTimeOfAppointment = null;
        Doctor oldDoctor = update.getDoctor();
        Patient oldPatient = update.getPatient();
        Appointment updateOld = update;
        if (update == null) {
            throw new RuntimeException("Appointment not found");
        }
        if (doctor != null) {
            oldDoctor.getAppointmentList().remove(updateOld);
            doctorRepository.saveAndFlush(oldDoctor);
            update.setDoctor(doctor);
        }
        if (patient != null) {
            oldPatient.getAppointmentList().remove(updateOld);
            patientRepository.saveAndFlush(oldPatient);
            update.setPatient(patient);
        }
        if (request.getDateAndTimeOfAppointment() != null) {
            try {
                dateAndTimeOfAppointment = new SimpleDateFormat(pattern).parse(request.getDateAndTimeOfAppointment());
            } catch (ParseException e) {
                //todo Fix exception
                e.printStackTrace();
            }
            update.setDateAndTimeOfAppointment(dateAndTimeOfAppointment);
        }
        appointmentRepository.saveAndFlush(update);

        return "Appointment id=" + update.getId() + " updated";
    }

    @Override
    public String deleteAppointment(Long id) {
        Appointment request = appointmentRepository.getById(id);
        Patient patient = request.getPatient();
        Doctor doctor = request.getDoctor();
        doctor.getAppointmentList().remove(request);
        patient.getAppointmentList().remove(request);
        doctorRepository.saveAndFlush(doctor);
        patientRepository.saveAndFlush(patient);
        appointmentRepository.deleteById(id);
        return "Appointment id=" + id + " deleted";
    }

    private Appointment convertRequestToAppointment(AppointmentRequest request) {
        Doctor doctor = doctorRepository.findDoctorById(request.getIdDoctor());
        Patient patient = patientRepository.getById(request.getIdPatient());
        if (doctor == null) {
            throw new RuntimeException("Doctor not found");
        }
        if (patient == null) {
            throw new RuntimeException("Patient not found");
        }
        Date dateAndTimeOfAppointment = null;
        try {
            dateAndTimeOfAppointment = new SimpleDateFormat(pattern).parse(request.getDateAndTimeOfAppointment());
        } catch (ParseException e) {
            //todo Fix exception
            e.printStackTrace();
        }

        return new Appointment(request.getId(), patient, doctor, dateAndTimeOfAppointment);
    }

    private AppointmentResponce convertAppointmentToResponce(Appointment responce) {
        SimpleDateFormat convert = new SimpleDateFormat(pattern);

        return new AppointmentResponce(responce.getId(),
                responce.getPatient().getName(),
                responce.getDoctor().getName(),
                convert.format(responce.getDateAndTimeOfAppointment()));
    }
}
