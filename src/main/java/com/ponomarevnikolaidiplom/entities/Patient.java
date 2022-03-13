package com.ponomarevnikolaidiplom.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String numberOfInsurance;
    @Column(nullable = false)
    private String name;
    private String phoneNumber;
    private String address;
    @OneToMany
    private List<Appointment> appointmentList;
}
