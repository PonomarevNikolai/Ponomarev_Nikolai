package com.ponomarevnikolaidiplom.controllers;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest()
@AutoConfigureMockMvc
@Slf4j

class PatientControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void getAllPatients() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/patient/all"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getPatient() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/patient/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":"+null+"," +
                                "\"numberOfInsurance\":\"123456789\" ," +
                                "\"name\":\"Petrov Vasiliy Victorovich\" ," +
                                "\"phoneNumber\":\"987654321\" ," +
                                "\"address\":\"SPB\"}")
                        .header("Content-Type", "application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("numberOfInsurance").value("123456789"))
                .andExpect(MockMvcResultMatchers.jsonPath("name").value("Petrov Vasiliy Victorovich"))
                .andReturn();
        String responce =result.getResponse().getContentAsString();
        JSONObject jsonObj= new JSONObject(responce);
        String id= jsonObj.getString("id");
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/patient/"+id))
                .andExpect(MockMvcResultMatchers.jsonPath("numberOfInsurance").value("123456789"))
                .andExpect(MockMvcResultMatchers.jsonPath("name").value("Petrov Vasiliy Victorovich"));
    }

    @Test
    void savePatient() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/patient/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":"+null+"," +
                                "\"numberOfInsurance\":\"123456789\" ," +
                                "\"name\":\"Petrov Vasiliy Victorovich\" ," +
                                "\"phoneNumber\":\"987654321\" ," +
                                "\"address\":\"SPB\"}")
                        .header("Content-Type", "application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("numberOfInsurance").value("123456789"))
                .andExpect(MockMvcResultMatchers.jsonPath("name").value("Petrov Vasiliy Victorovich"));

    }

    @Test
    void updatePatient() throws Exception {

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/patient/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":"+null+"," +
                                "\"numberOfInsurance\":\"123456789\" ," +
                                "\"name\":\"Petrov Vasiliy Victorovich\" ," +
                                "\"phoneNumber\":\"987654321\" ," +
                                "\"address\":\"SPB\"}")
                        .header("Content-Type", "application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("numberOfInsurance").value("123456789"))
                .andExpect(MockMvcResultMatchers.jsonPath("name").value("Petrov Vasiliy Victorovich"))
                .andReturn();
        String responce =result.getResponse().getContentAsString();
        JSONObject jsonObj= new JSONObject(responce);
        String id= jsonObj.getString("id");
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/patient/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":"+id+"," +
                                "\"numberOfInsurance\":\"111222333\" ," +
                                "\"name\":\"Korsakova Svetlana Vladimirovna\" ," +
                                "\"phoneNumber\":\"987654321\" ," +
                                "\"address\":\"SPB\"}")
                        .header("Content-Type", "application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/patient/"+id))
                .andExpect(MockMvcResultMatchers.jsonPath("numberOfInsurance").value("111222333"))
                .andExpect(MockMvcResultMatchers.jsonPath("name").value("Korsakova Svetlana Vladimirovna"));
    }

    @Test
    void deletePatient() throws Exception {

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/patient/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":"+null+"," +
                                "\"numberOfInsurance\":\"123456789\" ," +
                                "\"name\":\"Petrov Vasiliy Victorovich\" ," +
                                "\"phoneNumber\":\"987654321\" ," +
                                "\"address\":\"SPB\"}")
                        .header("Content-Type", "application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("numberOfInsurance").value("123456789"))
                .andExpect(MockMvcResultMatchers.jsonPath("name").value("Petrov Vasiliy Victorovich"))
                .andReturn();
        String responce =result.getResponse().getContentAsString();
        JSONObject jsonObj= new JSONObject(responce);
        String id= jsonObj.getString("id");

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/patient/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":"+id+"}")
                        .header("Content-Type", "application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(print());


    }
}