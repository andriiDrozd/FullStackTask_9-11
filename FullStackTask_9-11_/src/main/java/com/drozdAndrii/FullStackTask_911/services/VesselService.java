package com.drozdAndrii.FullStackTask_911.services;

import com.drozdAndrii.FullStackTask_911.exceptions.CargoExceptions.CargoAlreadyAssignedException;
import com.drozdAndrii.FullStackTask_911.exceptions.CargoExceptions.CargoNotFoundException;
import com.drozdAndrii.FullStackTask_911.exceptions.VesselExceptions.VesselNotFoundException;
import com.drozdAndrii.FullStackTask_911.model.Cargo;
import com.drozdAndrii.FullStackTask_911.model.Vessel;
import com.drozdAndrii.FullStackTask_911.repositories.VesselRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Transactional(readOnly = true)
public class VesselService {
    private final VesselRepository vesselRepository;
    private final CargoService cargoService;

    @Autowired
    public VesselService(VesselRepository vesselRepository, CargoService cargoService) {
        this.vesselRepository = vesselRepository;
        this.cargoService = cargoService;
    }

    public List<Vessel> getAllVessels() {
        return vesselRepository.findAll();
    }

    public Vessel getVesselById(int id) {
        return vesselRepository.findById(id).orElseThrow(() ->
                new VesselNotFoundException(id));
    }

    public Vessel addVessel(Vessel vessel) {
        return vesselRepository.save(vessel);
    }

    @Transactional
    public Vessel deleteVessel(int id) {
        Vessel vessel = getVesselById(id);
        vesselRepository.delete(vessel);
        return vessel;
    }

    @Transactional
    public Vessel editVessel(int id, Vessel vessel) {
        Vessel vesselToEdit = getVesselById(id);
        vesselToEdit.setVesselName(vessel.getVesselName());
        vesselToEdit.setYearOfBuild(vessel.getYearOfBuild());
        return vesselToEdit;
    }

    @Transactional
    public Vessel addCargoToVessel(int vesselId, int cargoId) {
        Vessel vessel = getVesselById(vesselId);
        Cargo cargo = cargoService.getCargoById(cargoId);
        if (Objects.nonNull(cargo.getTrucker())) {
            throw new CargoAlreadyAssignedException(cargoId, cargo.getTrucker().getId());
        }
        vessel.getCargoes().add(cargo);
        cargo.setTrucker(vessel);
        return vessel;
    }

    @Transactional
    public Vessel deleteCargoFromVessel(int vesselId, int cargoId) {
        Vessel vessel = getVesselById(vesselId);
        Cargo cargo = cargoService.getCargoById(cargoId);
        vessel.getCargoes().remove(cargo);
        cargo.setTrucker(null);
        return vessel;
    }
}
