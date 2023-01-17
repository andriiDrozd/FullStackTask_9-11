package com.drozdAndrii.FullStackTask_911.controllers;

import com.drozdAndrii.FullStackTask_911.dto.CargoDTO;
import com.drozdAndrii.FullStackTask_911.exceptions.CargoErrorResponse;
import com.drozdAndrii.FullStackTask_911.exceptions.CargoExceptions.CargoNotCreatedException;
import com.drozdAndrii.FullStackTask_911.exceptions.CargoExceptions.CargoNotFoundException;
import com.drozdAndrii.FullStackTask_911.exceptions.CargoExceptions.CargoWithThisSendNumberAlreadyExistException;
import com.drozdAndrii.FullStackTask_911.exceptions.VesselErrorResponse;
import com.drozdAndrii.FullStackTask_911.exceptions.VesselExceptions.VesselNotFoundException;
import com.drozdAndrii.FullStackTask_911.model.Cargo;
import com.drozdAndrii.FullStackTask_911.services.CargoService;
import com.drozdAndrii.FullStackTask_911.utils.BindingResultSender;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_TIME;

@RestController
@RequestMapping("/cargo")
@JsonSerialize
public class CargoController {

    private final CargoService cargoService;

    @Autowired
    public CargoController(CargoService cargoService) {
        this.cargoService = cargoService;
    }

    @GetMapping
    public ResponseEntity<List<CargoDTO>> getAllCargo() {
        List<Cargo> cargoList = cargoService.getAllCargo();
        List<CargoDTO> cargoesDTO = cargoList.stream().map(CargoDTO::from).collect(Collectors.toList());
        return new ResponseEntity<>(cargoesDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CargoDTO> createCargo(@RequestBody @Valid CargoDTO cargoDTO,
                                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg= BindingResultSender.errorMsg(bindingResult);
            throw new CargoNotCreatedException(errorMsg.toString());
        }
        Cargo cargo = Cargo.from(cargoDTO);
        cargoService.checkExistingSendNumber(cargo);
        if( cargoService.checkExistingSendNumber(cargo)){
            throw new CargoWithThisSendNumberAlreadyExistException(String.valueOf(cargo.getSendNumber()));
        }
        cargoService.saveCargo(cargo);
        return new ResponseEntity<>(CargoDTO.from(cargo), HttpStatus.OK);
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<CargoDTO> getCargo(@PathVariable final int id) {
        Cargo cargo = cargoService.getCargoById(id);
        return new ResponseEntity<>(CargoDTO.from(cargo), HttpStatus.OK);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<CargoDTO> deleteCargo(@PathVariable final int id) {
        Cargo cargo = cargoService.deleteCargo(id);
        return new ResponseEntity<>(CargoDTO.from(cargo), HttpStatus.OK);
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<CargoDTO> editCargo(@PathVariable final int id,
                                              @RequestBody @Valid CargoDTO cargoDTO,
                                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg= BindingResultSender.errorMsg(bindingResult);
            throw new CargoNotCreatedException(errorMsg.toString());
        }
        Cargo editedCargo = cargoService.editCargo(id, Cargo.from(cargoDTO));
        return new ResponseEntity<>(CargoDTO.from(editedCargo), HttpStatus.OK);
    }

//    @GetMapping(value = "{cargoName}/sendNumber/{sendNumber}/get")
//    public ResponseEntity<CargoDTO> getCargoByCargoNameAndSendNumber(@PathVariable String cargoName,
//                                                                     @PathVariable int sendNumber) {
//        Cargo cargo = cargoService.getByCargoNameAndSendNumber(cargoName, sendNumber);
//        System.out.println(cargo.toString());
//        return new ResponseEntity<>(CargoDTO.from(cargo), HttpStatus.OK);
//    }

    @GetMapping("/page/{page}/cargoName/{cargoName}/sendNumber/{sendNumber}/get")
    public ResponseEntity<List<CargoDTO>> getAllWithPagination(@PathVariable int page,

                                                               @PathVariable String cargoName,
                                                               @PathVariable int sendNumber){

        List<Cargo> cargoList = cargoService.findWithPagination(page, 1,cargoName,sendNumber);
        List<CargoDTO> cargoesDTO = cargoList.stream().map(CargoDTO::from).collect(Collectors.toList());
        return new ResponseEntity<>(cargoesDTO, HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<CargoErrorResponse> handleException(CargoNotFoundException e) {
        CargoErrorResponse response = new CargoErrorResponse(e.getMessage());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<CargoErrorResponse> handleException(CargoNotCreatedException e) {
        CargoErrorResponse response = new CargoErrorResponse(e.getMessage());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<CargoErrorResponse> handleException(CargoWithThisSendNumberAlreadyExistException e) {
        CargoErrorResponse response = new CargoErrorResponse(e.getMessage());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
