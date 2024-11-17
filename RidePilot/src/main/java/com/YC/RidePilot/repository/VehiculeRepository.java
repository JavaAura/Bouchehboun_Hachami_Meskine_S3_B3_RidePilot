package com.YC.RidePilot.repository;

import com.YC.RidePilot.entity.Vehicule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehiculeRepository  extends JpaRepository<Vehicule,Long> {
}
