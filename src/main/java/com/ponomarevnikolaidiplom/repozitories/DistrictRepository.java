package com.ponomarevnikolaidiplom.repozitories;

import com.ponomarevnikolaidiplom.entities.District;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DistrictRepository extends JpaRepository<District, Long> {
}