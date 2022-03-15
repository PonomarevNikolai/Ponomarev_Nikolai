package com.ponomarevnikolaidiplom.configuration;

import com.ponomarevnikolaidiplom.entities.Specialization;
import com.ponomarevnikolaidiplom.repozitories.SpecializationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

@Configuration
@RequiredArgsConstructor
public class FirstInit {
    final SpecializationRepository specializationRepository;

    @Bean
    public void initSpecialization(){
        if(specializationRepository.findAll().isEmpty()){
            for (int i = 0; i < 100; i++) {
                specializationRepository.save(new Specialization(null,"Specialization "+(i+1), new ArrayList<>(),new ArrayList<>()));
            }
        }
    }

}
