package com.YC.RidePilot.services;

import com.YC.RidePilot.entity.Vehicule;
import com.YC.RidePilot.repository.VehiculeRepository;
import com.YC.RidePilot.services.InterfacesServices.IVehiculeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehiculeService implements IVehiculeService {


    private final VehiculeRepository vehiculeRepository;

    @Autowired
    public VehiculeService(VehiculeRepository vehiculeRepository){
        this.vehiculeRepository = vehiculeRepository;
    }

    @Override
    public List<Vehicule> getAllVehicules() {
        return  vehiculeRepository.findAll();
    }

}
