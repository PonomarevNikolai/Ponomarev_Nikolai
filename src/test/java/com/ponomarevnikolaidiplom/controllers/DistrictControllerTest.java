package com.ponomarevnikolaidiplom.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ponomarevnikolaidiplom.dto.responce.AppointmentResponce;
import com.ponomarevnikolaidiplom.dto.responce.DistrictResponce;
import com.ponomarevnikolaidiplom.dto.responce.DoctorResponce;
import com.ponomarevnikolaidiplom.dto.responce.PatientResponce;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest()
@AutoConfigureMockMvc
@Slf4j
class DistrictControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper = new ObjectMapper();


    @Test
    void getAllDistrict() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/district/all"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(print());
    }

    @Test
    void getDistrict() throws Exception {
        MvcResult districtAdd = saveDistrictMethod();
        String responce=districtAdd.getResponse().getContentAsString();
        DistrictResponce districtResponce=objectMapper.readValue(responce,DistrictResponce.class);
        MvcResult districtGet= mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/district/"+districtResponce.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String districtGetResponce = districtGet.getResponse().getContentAsString();
        DistrictResponce districtResponce2 = objectMapper.readValue(districtGetResponce, DistrictResponce.class);
        assertEquals(districtResponce,districtResponce2);
    }

    @Test
    void saveDistrict() throws Exception {
        MvcResult districtAdd = saveDistrictMethod();
        String responce=districtAdd.getResponse().getContentAsString();
        DistrictResponce districtResponce=objectMapper.readValue(responce,DistrictResponce.class);
        assertTrue(districtResponce.getName().equals("Test"));
    }

    @Test
    void updateDistrict() throws Exception {
        MvcResult districtAdd = saveDistrictMethod();
        String responce=districtAdd.getResponse().getContentAsString();
        DistrictResponce districtResponce=objectMapper.readValue(responce,DistrictResponce.class);
        MvcResult districtUpdate= mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/district/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":" + districtResponce.getId()
                        + ", \"name\": \"Test2\" }")
                .header("Content-Type", "application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String districtUpdateResponce = districtUpdate.getResponse().getContentAsString();
        assertTrue(districtUpdateResponce.contains("updated District  id="+districtResponce.getId()+" name=Test2"));

    }

    @Test
    void deleteDistrict() throws Exception {
        MvcResult districtAdd = saveDistrictMethod();
        String responce=districtAdd.getResponse().getContentAsString();
        DistrictResponce districtResponce=objectMapper.readValue(responce,DistrictResponce.class);
        MvcResult districtDelete= mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/district/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":" + districtResponce.getId()
                                + " }")
                        .header("Content-Type", "application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String districtDeleteResponce = districtDelete.getResponse().getContentAsString();
        assertTrue(districtDeleteResponce.contains("deleted District id="+districtResponce.getId()));

    }

    private MvcResult saveDistrictMethod() throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/district/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":" + null
                                + ", \"name\": \"Test\" }")
                        .header("Content-Type", "application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn();
    }
}