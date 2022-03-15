package com.ponomarevnikolaidiplom.repozitories;

import com.ponomarevnikolaidiplom.entities.Specialization;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpecializationRepository extends JpaRepository<Specialization, Long> {
    Page findAll(Pageable pageable);
}