package com.melck.doctor.ms.resources;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import com.melck.doctor.ms.entities.Case;
import com.melck.doctor.ms.services.exceptions.DataIntegrityViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.melck.doctor.ms.dtos.CaseDTO;
import com.melck.doctor.ms.dtos.ResponseCaseDTO;
import com.melck.doctor.ms.services.CaseService;
import com.melck.doctor.ms.services.exceptions.ResourceNotFoundException;
import com.melck.doctor.ms.utils.Factory;
@Disabled
@SpringBootTest
@AutoConfigureMockMvc
class DoctorLabellingResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CaseService service;

    @Autowired
    private ObjectMapper objectMapper;

    private Long existingId;
    private Long nonExistingId;
    private Long dependentId;
    private Case aCase;
    private CaseDTO caseDTO;
    private ResponseCaseDTO respDTO;
    private PageImpl<ResponseCaseDTO> page;

    @BeforeEach
    void setUp() throws Exception {
        existingId = 1L;
        nonExistingId = 2L;
        dependentId = 3L;
        aCase = Factory.createCase();
        caseDTO = Factory.createCaseDTO();
        respDTO = Factory.createResponseCaseDTO();

        page = new PageImpl<>(List.of(respDTO));

        when(service.findAllCases(any())).thenReturn(page);

        when(service.findById(existingId)).thenReturn(aCase);
        when(service.findById(nonExistingId)).thenThrow(ResourceNotFoundException.class);

        when(service.insert(any())).thenReturn(respDTO);

        when(service.update(eq(existingId), any())).thenReturn(aCase);
        when(service.update(eq(nonExistingId), any())).thenThrow(ResourceNotFoundException.class);

        doNothing().when(service).delete(existingId);
        doThrow(ResourceNotFoundException.class).when(service).delete(nonExistingId);
        doThrow(DataIntegrityViolationException.class).when(service).delete(dependentId);
    }

    @Test
    public void insertCaseShouldReturnRespCaseDTOCreated() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(caseDTO);

        ResultActions result =
                mockMvc.perform(post("/doctor-labellings/cases")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isCreated());
        result.andExpect(jsonPath("$.caseId").exists());
        result.andExpect(jsonPath("$.caseDescription").exists());
    }

    @Test
    public void findCaseByIdShouldReturnResponseCaseDtoWhenIdExists() throws Exception {
        ResultActions result =
                mockMvc.perform(get("/doctor-labellings/cases/{id}", existingId)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.caseId").exists());
        result.andExpect(jsonPath("$.caseDescription").exists());
        result.andExpect(jsonPath("$.caseId").value(existingId));
    }

    @Test
    public void findCaseByIdShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
        ResultActions result =
                mockMvc.perform(get("/doctor-labellings/cases/{id}", nonExistingId)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());
    }

    @Test
    public void findAllCasesShouldReturnPage() throws Exception {
        ResultActions result =
                mockMvc.perform(get("/doctor-labellings/cases")
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
    }

    @Test
    public void updateCaseShouldReturnProductDTOWhenIdExists() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(aCase);

        ResultActions result =
                mockMvc.perform(put("/doctor-labellings/cases/{id}", existingId)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.caseId").exists());
        result.andExpect(jsonPath("$.caseDescription").exists());
        result.andExpect(jsonPath("$.caseId").value(existingId));
    }

    @Test
    public void updateShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(aCase);

        ResultActions result =
                mockMvc.perform(put("/doctor-labellings/cases/{id}", nonExistingId)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());
    }

}