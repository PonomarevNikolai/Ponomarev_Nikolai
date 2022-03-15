package com.ponomarevnikolaidiplom.services.classes;

import com.ponomarevnikolaidiplom.dto.request.AppointmentRequest;
import com.ponomarevnikolaidiplom.dto.responce.AppointmentResponce;
import com.ponomarevnikolaidiplom.entities.Appointment;
import com.ponomarevnikolaidiplom.entities.Doctor;
import com.ponomarevnikolaidiplom.entities.Patient;
import com.ponomarevnikolaidiplom.exceptionhandler.TypicalError;
import com.ponomarevnikolaidiplom.exceptions.ServiceException;
import com.ponomarevnikolaidiplom.repozitories.AppointmentRepository;
import com.ponomarevnikolaidiplom.repozitories.DoctorRepository;
import com.ponomarevnikolaidiplom.repozitories.PatientRepository;
import com.ponomarevnikolaidiplom.services.interfacies.AppointmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppointmentServiceImp implements AppointmentService {

    final AppointmentRepository appointmentRepository;
    final PatientRepository patientRepository;
    final DoctorRepository doctorRepository;
    private static final String PATTERN = "dd.MM.yyyy hh:mm";
    private static final String APPOINTMENT_NOT_FOUND = "Appointment not found";

    @Override
    public AppointmentResponce saveAppointment(AppointmentRequest request) throws ServiceException{
        Optional<Doctor> doctorOptional = doctorRepository.findById(request.getIdDoctor());

        if (doctorOptional.isEmpty()) {
            throw new ServiceException("Doctor not found", TypicalError.NOT_FOUND);
        }

        Optional<Patient> patientOptional = patientRepository.findById(request.getIdPatient());
        if (patientOptional.isEmpty()) {
            throw new ServiceException("Patient not found", TypicalError.NOT_FOUND);
        }
        Appointment appointment = appointmentRepository.save(convertRequestToAppointment(request));
        Date dateOfAppointment = appointment.getDateAndTimeOfAppointment();
        Doctor doctor = doctorRepository.findDoctorById(request.getIdDoctor());
        Patient patient = patientRepository.getById(request.getIdPatient());
        doctor.getAppointmentList().forEach(appointment1 -> {
            if (appointment1.getDateAndTimeOfAppointment().compareTo(dateOfAppointment) == 0) {
                try {
                    throw new ServiceException("This doctor appointment time closed", TypicalError.BAD_REQUEST);
                } catch (ServiceException e) {
                    e.printStackTrace();
                }

            }
        });

        patient.getAppointmentList().forEach(appointment1 -> {
            if (appointment1.getDateAndTimeOfAppointment().compareTo(dateOfAppointment) == 0) {
                try {
                    throw  new ServiceException("This patient appointment time closed", TypicalError.BAD_REQUEST);
                } catch (ServiceException e) {
                    e.printStackTrace();
                }


            }
        });

        doctor.getAppointmentList().add(appointment);
        patient.getAppointmentList().add(appointment);
        doctorRepository.saveAndFlush(doctor);
        patientRepository.saveAndFlush(patient);
        log.info("Appointment added patient name={} doctor name={} date={}", patient.getName(), doctor.getName(), appointment.getDateAndTimeOfAppointment());
        return convertAppointmentToResponce(appointment);
    }

    @Override
    public AppointmentResponce getAppointment(Long id) throws ServiceException {
        Optional<Appointment> appointmentOptional = appointmentRepository.findById(id);

        if (appointmentOptional.isEmpty()) {

            throw new ServiceException(APPOINTMENT_NOT_FOUND, TypicalError.NOT_FOUND);
        }

        Appointment appointment = appointmentRepository.getById(id);

        return convertAppointmentToResponce(appointment);
    }

    @Override
    public List<AppointmentResponce> getAllAppointment(int page, int size) {
        Page<Appointment> appointmentPage=appointmentRepository.findAll(PageRequest.of(page, size));
        List<AppointmentResponce> appointmentResponceList = new ArrayList<>();
        appointmentPage.forEach(appointment -> appointmentResponceList.add(convertAppointmentToResponce(appointment)));
        return appointmentResponceList;
    }

    @Override
    public String updateAppointment(AppointmentRequest request) throws ServiceException {
        Optional<Appointment> appointmentOptional = appointmentRepository.findById(request.getId());

        if (appointmentOptional.isEmpty()) {
            throw new ServiceException(APPOINTMENT_NOT_FOUND, TypicalError.NOT_FOUND);
        }

        Appointment update = appointmentRepository.getById(request.getId());
        Date dateAndTimeOfAppointment;



        Appointment updateOld = update;
        Doctor oldDoctor = update.getDoctor();
        Patient oldPatient = update.getPatient();

        if (request.getIdDoctor() != null) {
            Doctor doctor = doctorRepository.findDoctorById(request.getIdDoctor());
            oldDoctor.getAppointmentList().remove(updateOld);
            doctorRepository.saveAndFlush(oldDoctor);
            update.setDoctor(doctor);
        }

        if (request.getIdPatient() != null) {
            Patient patient = patientRepository.getById(request.getIdPatient());
            oldPatient.getAppointmentList().remove(updateOld);
            patientRepository.saveAndFlush(oldPatient);
            update.setPatient(patient);
        }

        if (request.getDateAndTimeOfAppointment() != null) {
            try {
                dateAndTimeOfAppointment = new SimpleDateFormat(PATTERN).parse(request.getDateAndTimeOfAppointment());
            } catch (ParseException e) {

                throw new ServiceException("Wrong date and time format want be "+ PATTERN, TypicalError.BAD_REQUEST);
            }
            update.setDateAndTimeOfAppointment(dateAndTimeOfAppointment);
        }

        appointmentRepository.saveAndFlush(update);

        return "Appointment id=" + update.getId() + " updated";
    }

    @Override
    public String deleteAppointment(Long id) throws ServiceException {
        Optional<Appointment> appointmentOptional = appointmentRepository.findById(id);

        if (appointmentOptional.isEmpty()) {
            throw new ServiceException(APPOINTMENT_NOT_FOUND, TypicalError.NOT_FOUND);
        }
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

    private Appointment convertRequestToAppointment(AppointmentRequest request) throws ServiceException {
        Optional<Doctor> doctorOptional = doctorRepository.findById(request.getIdDoctor());

        if (doctorOptional.isEmpty()) {
            throw new ServiceException("Doctor not found", TypicalError.NOT_FOUND);
        }

        Optional<Patient> patientOptional = patientRepository.findById(request.getIdPatient());
        if (patientOptional.isEmpty()) {
            throw new ServiceException("Patient not found", TypicalError.NOT_FOUND);
        }
        Doctor doctor = doctorRepository.findDoctorById(request.getIdDoctor());
        Patient patient = patientRepository.getById(request.getIdPatient());
        Date dateAndTimeOfAppointment ;
        try {
            dateAndTimeOfAppointment = new SimpleDateFormat(PATTERN).parse(request.getDateAndTimeOfAppointment());
        } catch (ParseException e) {
            throw new ServiceException("Wrong date and time format want be "+ PATTERN, TypicalError.BAD_REQUEST);

        }

        return new Appointment(request.getId(), patient, doctor, dateAndTimeOfAppointment);
    }

    private AppointmentResponce convertAppointmentToResponce(Appointment responce) {
        SimpleDateFormat convert = new SimpleDateFormat(PATTERN);

        return new AppointmentResponce(responce.getId(),
                responce.getPatient().getName(),
                responce.getDoctor().getName(),
                convert.format(responce.getDateAndTimeOfAppointment()));
    }
}
