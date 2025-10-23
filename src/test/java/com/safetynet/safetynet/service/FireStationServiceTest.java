package com.safetynet.safetynet.service;

import com.safetynet.safetynet.model.FireStation;
import com.safetynet.safetynet.repository.FireStationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
public class FireStationServiceTest {

    @MockitoBean
    private FireStationRepository fireStationRepository;

    @Autowired
    private FireStationService fireStationService;

    private FireStation fireStation1;
    private FireStation fireStation2;

    @BeforeEach
    void setUp() {
        fireStation1 = new FireStation("10 Elm St", "1");
        fireStation2 = new FireStation("20 Oak St", "2");
    }

    @Test
    void testAddFireStation() {
        fireStationService.addFireStation(fireStation1);
        verify(fireStationRepository, times(1)).addFireStation(fireStation1);
    }

    @Test
    void testUpdateFireStation() {
        when(fireStationRepository.updateFireStation("10 Elm St", fireStation1)).thenReturn(fireStation1);

        FireStation updated = fireStationService.updateFireStation("10 Elm St", fireStation1);
        assertThat(updated).isEqualTo(fireStation1);

        verify(fireStationRepository, times(1)).updateFireStation("10 Elm St", fireStation1);
    }

    @Test
    void testDeleteFireStation() {
        when(fireStationRepository.deleteFireStation("10 Elm St")).thenReturn(true);

        boolean result = fireStationService.deleteFireStation("10 Elm St");
        assertThat(result).isTrue();

        verify(fireStationRepository, times(1)).deleteFireStation("10 Elm St");
    }

    @Test
    void testGetAllFireStations() {
        when(fireStationRepository.getAllFireStations()).thenReturn(List.of(fireStation1, fireStation2));

        List<FireStation> stations = fireStationService.getAllFireStations();
        assertThat(stations).hasSize(2);
        assertThat(stations).containsExactly(fireStation1, fireStation2);

        verify(fireStationRepository, times(1)).getAllFireStations();
    }
}
