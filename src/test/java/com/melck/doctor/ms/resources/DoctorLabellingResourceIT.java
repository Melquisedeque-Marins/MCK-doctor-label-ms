package com.melck.doctor.ms.resources;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.melck.doctor.ms.entities.Case;
import com.melck.doctor.ms.repositories.CaseRepository;
import com.melck.doctor.ms.utils.Factory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
@Disabled
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class DoctorLabellingResourceIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CaseRepository repository;

    private Long existingId;
    private Long nonExistingId;
    private Long countTotalProducts;

    @BeforeEach
    void setUp() throws Exception {
        existingId = 1L;
        nonExistingId = 1000L;
        countTotalProducts = repository.count();
    }

    @Test
    public void findAllCasesShouldReturnSortedPageWhenSortByName() throws Exception {
        ResultActions result =
                mockMvc.perform(get("/doctor-labellings/cases?page=0&size=10")
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.content").exists());
        result.andExpect(jsonPath("$.content[0].caseDescription").value("Macbook Pro"));
        result.andExpect(jsonPath("$.content[1].caseDescription").value("PC Gamer"));
        result.andExpect(jsonPath("$.content[2].caseDescription").value("PC Gamer Alfa"));
    }

    @Test
    public void updateShouldReturnProductDTOWhenIdExists() throws Exception {
        Case aCase = Factory.createCase();
        String jsonBody = objectMapper.writeValueAsString(aCase);

        String expectedDescription = aCase.getCaseDescription();

        ResultActions result =
                mockMvc.perform(put("/doctor-labellings/cases/{id}", existingId)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.caseId").value(existingId));
        result.andExpect(jsonPath("$.caseDescription").value(expectedDescription));
    }

    @Test
    public void updateShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
        Case aCase = Factory.createCase();
        String jsonBody = objectMapper.writeValueAsString(aCase);

        ResultActions result =
                mockMvc.perform(put("/doctor-labellings/cases/{id}", nonExistingId)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());
    }
}
