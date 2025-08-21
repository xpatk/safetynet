package com.safetynet.safetynet.repository.impl;

import com.safetynet.safetynet.model.Firestation;
import com.safetynet.safetynet.repository.FirestationRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class FirestationRepositoryImpl implements FirestationRepository {

    private final List<Firestation> firestations = new ArrayList<>();

    @Override
    public List<Firestation> getAllFirestations() {
        return firestations;
    }

    @Override
    public Firestation addFirestation(Firestation firestation) {
        firestations.add(firestation);
        return firestation;
    }

    @Override
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

    @Override
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

