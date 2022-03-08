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



@SpringBootTest()
@AutoConfigureMockMvc
@Slf4j
class SpecializationControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void getAllSpecialization() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/specialization/all"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getSpecialization() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/specialization/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":"+null+",\"name\":\"surgeon\"}")
                        .header("Content-Type", "application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("name").value("surgeon"))
                .andReturn();
        String responce =result.getResponse().getContentAsString();
        JSONObject jsonObj = new JSONObject(responce);
        String id= jsonObj.getString("id");
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/specialization/"+id))
                .andExpect(MockMvcResultMatchers.jsonPath("name").value("surgeon"));
    }

    @Test
    void saveSpecialization() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/specialization/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":"+null+",\"name\":\"surgeon\"}")
                        .header("Content-Type", "application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("name").value("surgeon"));
    }

    @Test
    void updateSpecialization() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/specialization/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":"+null+",\"name\":\"surgeon\"}")
                        .header("Content-Type", "application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("name").value("surgeon"))
                .andReturn();
        String responce =result.getResponse().getContentAsString();
        JSONObject jsonObj = new JSONObject(responce);
        String id= jsonObj.getString("id");
        MvcResult result2 = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/specialization/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":"+id+",\"name\":\"surgeonUdate\"}").
                        header("Content-Type", "application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        responce =result2.getResponse().getContentAsString();

        assert(responce.contains("surgeonUdate"));

    }

    @Test
    void deleteSpecialization() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/specialization/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":"+null+",\"name\":\"surgeon\"}")
                        .header("Content-Type", "application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("name").value("surgeon"))
                .andReturn();
        String responce =result.getResponse().getContentAsString();
        JSONObject jsonObj = new JSONObject(responce);
        String id= jsonObj.getString("id");
        MvcResult result2 = mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/specialization/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":"+id+"}")
                        .header("Content-Type", "application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        responce =result2.getResponse().getContentAsString();

        assert(responce.contains("id="+id));
    }
}