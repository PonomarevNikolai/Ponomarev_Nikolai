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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest()
@AutoConfigureMockMvc
@Slf4j
class MedicalBillControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void getAllMedicalBills() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/medicalbill/all"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getMedicalBill() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/medicalbill/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":"+null+",\"name\":\"massage\",\"price\":\"2000\"}")
                        .header("Content-Type", "application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String responce =result.getResponse().getContentAsString();
        JSONObject jsonObj= new JSONObject(responce);
        String id= jsonObj.getString("id");
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/medicalbill/"+id))
                .andExpect(MockMvcResultMatchers.jsonPath("name").value("massage"));
    }

    @Test
    void saveMedicalBill() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/medicalbill/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":"+null+",\"name\":\"massage\",\"price\":\"2000\"}")
                        .header("Content-Type", "application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("name").value("massage"));

    }

    @Test
    void updateMedicalBill() throws Exception {

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/medicalbill/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":"+null+",\"name\":\"massage\",\"price\":\"2000\"}")
                        .header("Content-Type", "application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("name").value("massage"))
                .andReturn();
        String responce =result.getResponse().getContentAsString();
        JSONObject jsonObj = new JSONObject(responce);
        String id= jsonObj.getString("id");
         mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/medicalbill/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":"+id+",\"name\":\"newMassage\",\"price\":\"1500\"}").
                        header("Content-Type", "application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/medicalbill/"+id))
                .andExpect(MockMvcResultMatchers.jsonPath("name").value("newMassage"))
                .andExpect(MockMvcResultMatchers.jsonPath("price").value("1500"));

    }

    @Test
    void deleteMedicalBill() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/medicalbill/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":"+null+",\"name\":\"massage\",\"price\":\"2000\"}")
                        .header("Content-Type", "application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("name").value("massage"))
                .andReturn();
        String responce =result.getResponse().getContentAsString();
        JSONObject jsonObj = new JSONObject(responce);
        String id= jsonObj.getString("id");
        MvcResult result2 = mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/medicalbill/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":"+id+"}").
                        header("Content-Type", "application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String responce2 =result2.getResponse().getContentAsString();
        assert(responce2.contains("Услуга удалена id="+id));
    }

    @Test
    void addSpecializationToMedicalBill() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/specialization/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":"+null+",\"name\":\"surgeon\"}")
                        .header("Content-Type", "application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("name").value("surgeon"))
                .andReturn();
        String responce =result.getResponse().getContentAsString();
        JSONObject jsonObj = new JSONObject(responce);
        String idSpecialization= jsonObj.getString("id");

        MvcResult result2 = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/medicalbill/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":"+null+",\"name\":\"massage\",\"price\":\"2000\"}")
                        .header("Content-Type", "application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String responce2 =result2.getResponse().getContentAsString();
        JSONObject jsonObj2= new JSONObject(responce2);
        String idMedicalBill= jsonObj2.getString("id");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/medicalbill/addspecialization")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"idMedicalBill\":"+idMedicalBill+",\"idSpecilization\":"+idSpecialization+"}")
                        .header("Content-Type", "application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/medicalbill/"+idMedicalBill))
                .andExpect(MockMvcResultMatchers.jsonPath("name").value("massage"))
                .andExpect(MockMvcResultMatchers.jsonPath("specializationList").value("id="+idSpecialization+", name=surgeon"));
    }

    @Test
    void deleteSpecializationFromMedicalBill() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/specialization/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":"+null+",\"name\":\"surgeon\"}")
                        .header("Content-Type", "application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("name").value("surgeon"))
                .andReturn();
        String responce =result.getResponse().getContentAsString();
        JSONObject jsonObj = new JSONObject(responce);
        String idSpecialization= jsonObj.getString("id");

        MvcResult result2 = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/medicalbill/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":"+null+",\"name\":\"massage\",\"price\":\"2000\"}")
                        .header("Content-Type", "application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String responce2 =result2.getResponse().getContentAsString();
        JSONObject jsonObj2= new JSONObject(responce2);
        String idMedicalBill= jsonObj2.getString("id");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/medicalbill/addspecialization")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"idMedicalBill\":"+idMedicalBill+",\"idSpecilization\":"+idSpecialization+"}")
                        .header("Content-Type", "application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/medicalbill/deletespecialization")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"idMedicalBill\":"+idMedicalBill+",\"idSpecilization\":"+idSpecialization+"}")
                        .header("Content-Type", "application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        MvcResult result3 = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/medicalbill/"+idMedicalBill))
                .andExpect(MockMvcResultMatchers.jsonPath("name").value("massage"))
                .andReturn();

        String responce3 =result3.getResponse().getContentAsString();

        assertFalse(responce3.contains("id="+idSpecialization+", name=surgeon"));

    }
}