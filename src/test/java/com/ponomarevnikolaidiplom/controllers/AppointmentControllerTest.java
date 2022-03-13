package com.ponomarevnikolaidiplom.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ponomarevnikolaidiplom.dto.responce.AppointmentResponce;
import com.ponomarevnikolaidiplom.dto.responce.DoctorResponce;
import com.ponomarevnikolaidiplom.dto.responce.PatientResponce;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest()
@AutoConfigureMockMvc
@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AppointmentControllerTest {
    private DoctorResponce doctorResponce;
    private PatientResponce patientResponce;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper = new ObjectMapper();
    private  int time=10;

    @BeforeAll
    void firstLoad() throws Exception {
        MvcResult doctorAdd = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/doctor/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":" + null + ",\"name\":\"Petrov Vasiliy Victorovich\"}")
                        .header("Content-Type", "application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String doctorAddResponce = doctorAdd.getResponse().getContentAsString();
        this.doctorResponce = objectMapper.readValue(doctorAddResponce, DoctorResponce.class);

        MvcResult patientAdd = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/patient/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":" + null + "," +
                                "\"numberOfInsurance\":\"123456789\" ," +
                                "\"name\":\"Petrov Dmitriy Victorovich\" ," +
                                "\"phoneNumber\":\"987654321\" ," +
                                "\"address\":\"SPB\"}")
                        .header("Content-Type", "application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("numberOfInsurance").value("123456789"))
                .andExpect(MockMvcResultMatchers.jsonPath("name").value("Petrov Dmitriy Victorovich"))
                .andReturn();
        String patientAddResponce = patientAdd.getResponse().getContentAsString();
        this.patientResponce = objectMapper.readValue(patientAddResponce, PatientResponce.class);
    }

    @Test
    void getAllAppointment() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/appointment/all"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(print());

    }

    @Test
    void getAppointment() throws Exception {
        this.time=time+5;
        MvcResult appointmentAdd = saveAppointmentMethod();
        String appointmentAddResponce = appointmentAdd.getResponse().getContentAsString();
        AppointmentResponce appointmentResponce = objectMapper.readValue(appointmentAddResponce, AppointmentResponce.class);
        MvcResult appointmentGet= mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/appointment/"+appointmentResponce.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String appointmentGetResponce = appointmentGet.getResponse().getContentAsString();
        AppointmentResponce appointmentResponce2 = objectMapper.readValue(appointmentGetResponce, AppointmentResponce.class);
        assertEquals(appointmentResponce,appointmentResponce2);

    }

    @Test
    void saveAppointment() throws Exception {
        this.time=time+5;
        MvcResult appointmentAdd = saveAppointmentMethod();
        String appointmentAddResponce = appointmentAdd.getResponse().getContentAsString();
        AppointmentResponce appointmentResponce = objectMapper.readValue(appointmentAddResponce, AppointmentResponce.class);
        assertEquals(appointmentResponce.getNamePatient(),patientResponce.getName());
        assertEquals(appointmentResponce.getNameDoctor(),doctorResponce.getName());
    }

    @Test
    void updateAppointment() throws Exception {
        this.time=time+5;
        MvcResult appointmentAdd = saveAppointmentMethod();
        String appointmentAddResponce = appointmentAdd.getResponse().getContentAsString();
        AppointmentResponce appointmentResponce = objectMapper.readValue(appointmentAddResponce, AppointmentResponce.class);
        MvcResult appointmentDelete = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/appointment/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":" + appointmentResponce.getId()+",  \"dateAndTimeOfAppointment\": \"02.02.2022 08:13\"}")
                        .header("Content-Type", "application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(print())
                .andReturn();
        String appointmentUpdateResponce = appointmentDelete.getResponse().getContentAsString();
        assertTrue(appointmentUpdateResponce.contains("Appointment id=" + appointmentResponce.getId()+" updated"));
    }

    @Test
    void deleteAppointment() throws Exception {
        this.time=time+5;
        MvcResult appointmentAdd = saveAppointmentMethod();
        String appointmentAddResponce = appointmentAdd.getResponse().getContentAsString();
        AppointmentResponce appointmentResponce = objectMapper.readValue(appointmentAddResponce, AppointmentResponce.class);
        MvcResult appointmentDelete = mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/appointment/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":" + appointmentResponce.getId()+"}")
                .header("Content-Type", "application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(print())
                .andReturn();
        String appointmentDeleteResponce = appointmentDelete.getResponse().getContentAsString();
        assertTrue(appointmentDeleteResponce.contains("Appointment id="+appointmentResponce.getId()+" deleted"));
    }

    private MvcResult saveAppointmentMethod() throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/appointment/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":" + null
                                + ",\"idPatient\":" + patientResponce.getId()
                                + ", \"idDoctor\":" + doctorResponce.getId()
                                + ", \"dateAndTimeOfAppointment\": \"02.02.2022 08:"+time+"\"}")
                        .header("Content-Type", "application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn();
    }
}