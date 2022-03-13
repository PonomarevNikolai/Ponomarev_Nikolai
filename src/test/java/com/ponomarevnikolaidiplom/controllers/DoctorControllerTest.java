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

import static org.junit.jupiter.api.Assertions.assertFalse;


@SpringBootTest()
@AutoConfigureMockMvc
@Slf4j
class DoctorControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void getAllDoctors() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/doctor/all"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getDoctor() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/doctor/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":" + null + ",\"name\":\"Petrov Vasiliy Victorovich\"}")
                        .header("Content-Type", "application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String responce = result.getResponse().getContentAsString();
        JSONObject jsonObj = new JSONObject(responce);
        String id = jsonObj.getString("id");
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/doctor/" + id))
                .andExpect(MockMvcResultMatchers.jsonPath("name").value("Petrov Vasiliy Victorovich"));
    }

    @Test
    void saveDoctor() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/doctor/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":" + null + ",\"name\":\"Petrov Vasiliy Victorovich\"}")
                        .header("Content-Type", "application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String responce = result.getResponse().getContentAsString();
        assert (responce.contains("Petrov Vasiliy Victorovich"));

    }

    @Test
    void updateDoctor() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/doctor/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":" + null + ",\"name\":\"Petrov Vasiliy Victorovich\"}")
                        .header("Content-Type", "application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String responce = result.getResponse().getContentAsString();

        JSONObject jsonObj = new JSONObject(responce);
        String id = jsonObj.getString("id");
        MvcResult result2 = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/doctor/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":" + id + ",\"name\":\"Korsakova Svetlana Vladimirovna\"}")
                        .header("Content-Type", "application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        responce = result2.getResponse().getContentAsString();
        assert (responce.contains("Korsakova Svetlana Vladimirovna"));
    }

    @Test
    void deleteDoctor() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/doctor/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":" + null + ",\"name\":\"Petrov Vasiliy Victorovich\"}")
                        .header("Content-Type", "application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String responce = result.getResponse().getContentAsString();
        JSONObject jsonObj = new JSONObject(responce);
        String id = jsonObj.getString("id");
        MvcResult result2 = mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/doctor/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":" + id + "}")
                        .header("Content-Type", "application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        responce = result2.getResponse().getContentAsString();
        assert (responce.contains("id=" + id));
    }

    @Test
    void addSpecializationToDoctor() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/specialization/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":" + null + ",\"name\":\"surgeon\"}")
                        .header("Content-Type", "application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("name").value("surgeon"))
                .andReturn();
        String responce = result.getResponse().getContentAsString();
        JSONObject jsonObj = new JSONObject(responce);
        String idSpecialization = jsonObj.getString("id");
        MvcResult result2 = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/doctor/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":" + null + ",\"name\":\"Petrov Vasiliy Victorovich\"}")
                        .header("Content-Type", "application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String responce2 = result2.getResponse().getContentAsString();
        JSONObject jsonObj2 = new JSONObject(responce2);
        String idDoctor = jsonObj2.getString("id");
        System.out.println(idDoctor + " " + idSpecialization);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/doctor/addspecialization")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"idDoctor\":" + idDoctor + ",\"idSpecilization\":" + idSpecialization + "}")
                        .header("Content-Type", "application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        MvcResult result3 = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/doctor/" + idDoctor)).andReturn();
        String responce3 = result3.getResponse().getContentAsString();
        assert (responce3.contains("id = " + idSpecialization + ", name = surgeon"));

    }

    @Test
    void deleteSpecializationFromDoctor() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/specialization/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":" + null + ",\"name\":\"surgeon\"}")
                        .header("Content-Type", "application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("name").value("surgeon"))
                .andReturn();
        String responce = result.getResponse().getContentAsString();
        JSONObject jsonObj = new JSONObject(responce);
        String idSpecialization = jsonObj.getString("id");
        MvcResult result2 = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/doctor/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":" + null + ",\"name\":\"Petrov Vasiliy Victorovich\"}")
                        .header("Content-Type", "application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String responce2 = result2.getResponse().getContentAsString();
        JSONObject jsonObj2 = new JSONObject(responce2);
        String idDoctor = jsonObj2.getString("id");
        System.out.println(idDoctor + " " + idSpecialization);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/doctor/addspecialization")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"idDoctor\":" + idDoctor + ",\"idSpecilization\":" + idSpecialization + "}")
                        .header("Content-Type", "application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        MvcResult result3 = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/doctor/" + idDoctor)).andReturn();
        String responce3 = result3.getResponse().getContentAsString();
        assert (responce3.contains("id = " + idSpecialization + ", name = surgeon"));

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/doctor/deletespecialization")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"idDoctor\":" + idDoctor + ",\"idSpecilization\":" + idSpecialization + "}")
                        .header("Content-Type", "application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        MvcResult result4 = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/doctor/" + idDoctor)).andReturn();
        String responce4 = result4.getResponse().getContentAsString();

        assertFalse(responce4.contains("id = " + idSpecialization + ", name = surgeon"));

    }
}