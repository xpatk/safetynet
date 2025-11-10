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
import java.time.format.DateTimeFormatter;
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

    private MedicalRecord johnBoyd;
    private MedicalRecord jacobBoyd;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    /**
     * Initializes mock medical record data before each test.
     * <p>
     * Matches the structure of the actual JSON data from the application.
     * </p>
     */
    @BeforeEach
    void setUp() {
        johnBoyd = new MedicalRecord(
                "John",
                "Boyd",
                LocalDate.parse("03/06/1984", formatter),
                List.of("aznol:350mg", "hydrapermazol:100mg"),
                List.of("nillacilan")
        );

        jacobBoyd = new MedicalRecord(
                "Jacob",
                "Boyd",
                LocalDate.parse("03/06/1989", formatter),
                List.of("pharmacol:5000mg", "terazine:10mg", "noznazol:250mg"),
                List.of()
        );
    }

    /**
     * Tests retrieval of all medical records successfully.
     *
     * @throws Exception if request execution fails
     */
    @Test
    @DisplayName("GET all medical records - Success")
    void testGetAllMedicalRecordsSuccess() throws Exception {
        when(medicalRecordService.getAllMedicalRecords()).thenReturn(List.of(johnBoyd, jacobBoyd));

        mockMvc.perform(get("/medicalrecord"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[0].lastName").value("Boyd"))
                .andExpect(jsonPath("$[0].birthdate").value("03/06/1984"))
                .andExpect(jsonPath("$[0].medications[0]").value("aznol:350mg"))
                .andExpect(jsonPath("$[0].medications[1]").value("hydrapermazol:100mg"))
                .andExpect(jsonPath("$[0].allergies[0]").value("nillacilan"))
                .andExpect(jsonPath("$[1].firstName").value("Jacob"))
                .andExpect(jsonPath("$[1].lastName").value("Boyd"))
                .andExpect(jsonPath("$[1].birthdate").value("03/06/1989"))
                .andExpect(jsonPath("$[1].medications[0]").value("pharmacol:5000mg"))
                .andExpect(jsonPath("$[1].medications[1]").value("terazine:10mg"))
                .andExpect(jsonPath("$[1].medications[2]").value("noznazol:250mg"))
                .andExpect(jsonPath("$[1].allergies").isEmpty());
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
                        .content(objectMapper.writeValueAsString(johnBoyd)))
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
        when(medicalRecordService.updateMedicalRecord(eq("John"), eq("Boyd"), any(MedicalRecord.class)))
                .thenReturn(johnBoyd);

        mockMvc.perform(put("/medicalrecord/John/Boyd")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(johnBoyd)))
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
                        .content(objectMapper.writeValueAsString(johnBoyd)))
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
        when(medicalRecordService.deleteMedicalRecord("John", "Boyd")).thenReturn(true);

        mockMvc.perform(delete("/medicalrecord/John/Boyd"))
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
