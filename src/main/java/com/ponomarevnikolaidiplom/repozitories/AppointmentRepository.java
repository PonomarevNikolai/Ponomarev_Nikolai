package com.ponomarevnikolaidiplom.repozitories;

import com.ponomarevnikolaidiplom.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}