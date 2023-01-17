package com.drozdAndrii.FullStackTask_911.controllers;

import com.drozdAndrii.FullStackTask_911.dto.VesselDTO;
import com.drozdAndrii.FullStackTask_911.exceptions.CargoExceptions.CargoAlreadyAssignedException;
import com.drozdAndrii.FullStackTask_911.exceptions.CargoExceptions.CargoNotCreatedException;
import com.drozdAndrii.FullStackTask_911.exceptions.VesselErrorResponse;
import com.drozdAndrii.FullStackTask_911.exceptions.VesselExceptions.VesselNotCreatedException;
import com.drozdAndrii.FullStackTask_911.exceptions.VesselExceptions.VesselNotFoundException;
import com.drozdAndrii.FullStackTask_911.model.Vessel;
import com.drozdAndrii.FullStackTask_911.services.VesselService;
import com.drozdAndrii.FullStackTask_911.utils.BindingResultSender;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/vessel")
public class VesselController {

    private final VesselService vesselService;

    @Autowired
    public VesselController(VesselService vesselService) {
        this.vesselService = vesselService;
    }

    @Transactional
    @PostMapping
    public ResponseEntity<VesselDTO> addVessel(@RequestBody @Valid VesselDTO vesselDTO,
                                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
           StringBuilder errorMsg= BindingResultSender.errorMsg(bindingResult);
            throw new VesselNotCreatedException(errorMsg.toString());
        }
        Vessel vessel = vesselService.addVessel(Vessel.from(vesselDTO));
        return new ResponseEntity<>(VesselDTO.from(vessel), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<VesselDTO>> getAllVessels() {
        List<Vessel> vessels = vesselService.getAllVessels();
        List<VesselDTO> vesselsDTO = vessels.stream().map(VesselDTO::from).collect(Collectors.toList());
        return new ResponseEntity<>(vesselsDTO, HttpStatus.OK);
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<VesselDTO> getVessel(@PathVariable final int id) {
        Vessel vessel = vesselService.getVesselById(id);
        return new ResponseEntity<>(VesselDTO.from(vessel), HttpStatus.OK);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<VesselDTO> deleteVessel(@PathVariable final int id) {
        Vessel vessel = vesselService.deleteVessel(id);
        return new ResponseEntity<>(VesselDTO.from(vessel), HttpStatus.OK);
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<VesselDTO> editVessel(@PathVariable final int id,
                                                @RequestBody @Valid VesselDTO vesselDto,
                                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg= BindingResultSender.errorMsg(bindingResult);
            throw new VesselNotCreatedException(errorMsg.toString());
        }
        Vessel vessel = vesselService.editVessel(id, Vessel.from(vesselDto));
        return new ResponseEntity<>(VesselDTO.from(vessel), HttpStatus.OK);
    }

    @PostMapping(value = "{vesselId}/cargo/{cargoId}/add")
    public ResponseEntity<VesselDTO> addCargoToVessel(@PathVariable final int vesselId,
                                                      @PathVariable final int cargoId) {
        Vessel vessel = vesselService.addCargoToVessel(vesselId, cargoId);
        return new ResponseEntity<>(VesselDTO.from(vessel), HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping(value = "{vesselId}/cargo/{cargoId}/delete")
    public ResponseEntity<VesselDTO> deleteCargoFromVessel(@PathVariable final int vesselId,
                                                           @PathVariable final int cargoId) {
        Vessel vessel = vesselService.deleteCargoFromVessel(vesselId, cargoId);
        return new ResponseEntity<>(VesselDTO.from(vessel), HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<VesselErrorResponse> handleException(VesselNotFoundException e) {
        VesselErrorResponse response = new VesselErrorResponse(e.getMessage());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<VesselErrorResponse> handleException(CargoAlreadyAssignedException e) {
        VesselErrorResponse response = new VesselErrorResponse(e.getMessage());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<VesselErrorResponse> handleException(VesselNotCreatedException e) {
        VesselErrorResponse response = new VesselErrorResponse(e.getMessage());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }




}
