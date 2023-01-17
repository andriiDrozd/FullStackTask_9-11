package com.drozdAndrii.FullStackTask_911.services;

import com.drozdAndrii.FullStackTask_911.exceptions.CargoExceptions.CargoNotFoundException;
import com.drozdAndrii.FullStackTask_911.model.Cargo;
import com.drozdAndrii.FullStackTask_911.repositories.CargoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CargoService {
    private final CargoRepository cargoRepository;

    @Autowired
    public CargoService(CargoRepository cargoRepository) {
        this.cargoRepository = cargoRepository;
    }

    public List<Cargo> getAllCargo() {
        return cargoRepository.findAll();
    }

    public Cargo getCargoById(int id) {
        return cargoRepository.findById(id).orElseThrow(() ->
                new CargoNotFoundException(id));
    }

    public Cargo deleteCargo(int id) {
        Cargo cargo = getCargoById(id);
        cargoRepository.delete(cargo);
        return cargo;
    }

    @Transactional
    public Cargo editCargo(int id, Cargo cargo) {
        Cargo cargoToEdit = getCargoById(id);
        cargoToEdit.setCargoName(cargo.getCargoName());
        cargoToEdit.setSendNumber(cargo.getSendNumber());
        return cargoToEdit;
    }

    public Cargo saveCargo(Cargo cargo) {
        cargoRepository.save(cargo);
        return cargo;
    }

//    public Cargo getByCargoNameAndSendNumber(String cargoName, int sendNumber){
//        return cargoRepository.findByCargoNameAndSendNumber(cargoName,sendNumber);
//    }

    public Boolean checkExistingSendNumber(Cargo cargo){
        Cargo existingCargo= cargoRepository.findBySendNumber(cargo.getSendNumber());
        if(existingCargo!=null){
            return true;
        }
        return false;
    }

    public List<Cargo> findWithPagination(Integer page, Integer cargoPerPage,String cargoName, int sendNumber){
       return cargoRepository.findByCargoNameAndVessel_ID(cargoName,sendNumber,PageRequest.of(page, cargoPerPage));
    }

}
