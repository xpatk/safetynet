package com.safetynet.safetynet.repository;

import com.safetynet.safetynet.model.FireStation;
import java.util.List;

public interface FireStationRepository {
    List<FireStation> getAllFireStations();
    FireStation addFireStation(FireStation firestation);
    FireStation updateFireStation(String address, FireStation updatedFirestation);
    boolean deleteFireStation(String address);
}
