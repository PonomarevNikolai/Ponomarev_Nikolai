package com.ponomarevnikolaidiplom.repozitories;

import com.ponomarevnikolaidiplom.entities.District;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DistrictRepository extends JpaRepository<District, Long> {
    Page findAll(Pageable pageable);
}