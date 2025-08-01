package com.safetynet.safetynet.repository;

import com.safetynet.safetynet.model.Firestation;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class FirestationRepository {

    private final List<Firestation> firestations = new ArrayList<>();

    public List<Firestation> getAllFirestations() {
        return firestations;
    }

    public Firestation addFirestation(Firestation firestation) {
        firestations.add(firestation);
        return firestation;
    }

    public Firestation updateFirestation(String address, Firestation updatedFirestation) {
        for (int i = 0; i < firestations.size(); i++) {
            Firestation current = firestations.get(i);
            if (current.getAddress().equals(address)) {
                current.setStation(updatedFirestation.getStation());
                return current;
            }
        }
        return null;
    }

    public boolean deleteFirestation(String address) {
        Iterator<Firestation> iterator = firestations.iterator();
        while (iterator.hasNext()) {
            Firestation firestation = iterator.next();
            if (firestation.getAddress().equals(address)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }
}
