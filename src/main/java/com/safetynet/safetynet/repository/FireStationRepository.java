package com.safetynet.safetynet.repository;

import com.safetynet.safetynet.model.FireStation;
import java.util.List;

public interface FireStationRepository {
    List<FireStation> getAllFireStations();
    void addFireStation(FireStation fireStation);
    FireStation updateFireStation(String address, FireStation updatedFireStation);
    boolean deleteFireStation(String address);
}
