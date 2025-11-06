package com.safetynet.safetynet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynet.model.MedicalRecord;
import com.safetynet.safetynet.service.MedicalRecordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for {@link MedicalRecordController}.
 * <p>
 * Uses {@link WebMvcTest} to test medical record endpoints in isolation
 * with a mocked {@link MedicalRecordService}. Verifies CRUD operations for medical records.
 * </p>
 */
@WebMvcTest(MedicalRecordController.class)
class MedicalRecordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MedicalRecordService medicalRecordService;

    @Autowired
    private ObjectMapper objectMapper;

    private MedicalRecord mockRecord;
    private MedicalRecord updatedRecord;

    /**
     * Initializes mock medical record data before each test.
     */
    @BeforeEach
    void setUp() {
        mockRecord = new MedicalRecord();
        mockRecord.setFirstName("John");
        mockRecord.setLastName("Doe");
        mockRecord.setBirthdate(LocalDate.parse("1990-01-01"));
        mockRecord.setMedications(List.of("aznol:350mg"));
        mockRecord.setAllergies(List.of("peanut"));

        updatedRecord = new MedicalRecord();
        updatedRecord.setFirstName("John");
        updatedRecord.setLastName("Doe");
        updatedRecord.setBirthdate(LocalDate.parse("1990-01-01"));
        updatedRecord.setMedications(List.of("ibuprofen:200mg"));
        updatedRecord.setAllergies(List.of("strawberry"));
    }

    /**
     * Tests retrieval of all medical records successfully.
     *
     * @throws Exception if request execution fails
     */
    @Test
    @DisplayName("GET all medical records - Success")
    void testGetAllMedicalRecordsSuccess() throws Exception {
        when(medicalRecordService.getAllMedicalRecords()).thenReturn(List.of(mockRecord));

        mockMvc.perform(get("/medicalrecord"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[0].lastName").value("Doe"))
                .andExpect(jsonPath("$[0].birthdate").value("1990-01-01"))
                .andExpect(jsonPath("$[0].medications[0]").value("aznol:350mg"))
                .andExpect(jsonPath("$[0].allergies[0]").value("peanut"));
    }

    /**
     * Tests creation of a new medical record.
     *
     * @throws Exception if request execution fails
     */
    @Test
    @DisplayName("POST medical record - Created")
    void testAddMedicalRecordCreated() throws Exception {
        doNothing().when(medicalRecordService).addMedicalRecord(any(MedicalRecord.class));

        mockMvc.perform(post("/medicalrecord")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockRecord)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Created"));

        verify(medicalRecordService, times(1)).addMedicalRecord(any(MedicalRecord.class));
    }

    /**
     * Tests updating an existing medical record successfully.
     *
     * @throws Exception if request execution fails
     */
    @Test
    @DisplayName("PUT medical record - Success")
    void testUpdateMedicalRecordSuccess() throws Exception {
        when(medicalRecordService.updateMedicalRecord(eq("John"), eq("Doe"), any(MedicalRecord.class)))
                .thenReturn(updatedRecord);

        mockMvc.perform(put("/medicalrecord/John/Doe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedRecord)))
                .andExpect(status().isOk())
                .andExpect(content().string("The medical record has been updated successfully."));
    }

    /**
     * Tests updating a non-existing medical record (not found scenario).
     *
     * @throws Exception if request execution fails
     */
    @Test
    @DisplayName("PUT medical record - Not Found")
    void testUpdateMedicalRecordNotFound() throws Exception {
        when(medicalRecordService.updateMedicalRecord(eq("Jane"), eq("Smith"), any(MedicalRecord.class)))
                .thenReturn(null);

        mockMvc.perform(put("/medicalrecord/Jane/Smith")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedRecord)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("The medical record not found."));
    }

    /**
     * Tests deletion of a medical record successfully.
     *
     * @throws Exception if request execution fails
     */
    @Test
    @DisplayName("DELETE medical record - Success")
    void testDeleteMedicalRecordSuccess() throws Exception {
        when(medicalRecordService.deleteMedicalRecord("John", "Doe")).thenReturn(true);

        mockMvc.perform(delete("/medicalrecord/John/Doe"))
                .andExpect(status().isOk())
                .andExpect(content().string("The medical record has been successfully deleted."));
    }

    /**
     * Tests deletion of a non-existing medical record (not found scenario).
     *
     * @throws Exception if request execution fails
     */
    @Test
    @DisplayName("DELETE medical record - Not Found")
    void testDeleteMedicalRecordNotFound() throws Exception {
        when(medicalRecordService.deleteMedicalRecord("Jane", "Smith")).thenReturn(false);

        mockMvc.perform(delete("/medicalrecord/Jane/Smith"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("The medical record has not been deleted"));
    }
}
