package com.drozdAndrii.FullStackTask_911.dto;

import com.drozdAndrii.FullStackTask_911.model.Cargo;
import com.drozdAndrii.FullStackTask_911.model.Vessel;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class VesselDTO {

    private int id;
    @NotEmpty(message = "Vessel name should not be empty")
    @Size(min = 2, max = 100, message = "Vessel name should be between 2 and 100 characters")
    private String vesselName;
    @Min(value = 1801, message = "Year of built should be greater than 1800")
    @Max(value = 2023, message = "Year of built should be less than 2024")
    private int yearOfBuild;
    private List<CargoDTO> cargoDTOS =new ArrayList<>();

    public VesselDTO() {
    }

    public VesselDTO(int id, String vesselName, int yearOfBuild, List<CargoDTO> cargoesDTO) {
        this.id = id;
        this.vesselName = vesselName;
        this.yearOfBuild = yearOfBuild;
        this.cargoDTOS = cargoesDTO;
    }

    public VesselDTO(String vesselName, int yearOfBuild, List<CargoDTO> cargoesDTO) {
        this.vesselName = vesselName;
        this.yearOfBuild = yearOfBuild;
        this.cargoDTOS = cargoesDTO;
    }

    public static VesselDTO from(Vessel vessel){
        VesselDTO vesselDTO=new VesselDTO();
        vesselDTO.setVesselName(vessel.getVesselName());
        vesselDTO.setYearOfBuild(vessel.getYearOfBuild());
        vesselDTO.setId(vessel.getId());
        if(vessel.getCargoes()!=null && vesselDTO.getCargoDTOS()!=null) {
            vesselDTO.setCargoDTOS(vessel.getCargoes().stream().map(CargoDTO::from).collect(Collectors.toList()));
        }
        return vesselDTO;
    }

    public List<CargoDTO> getCargoDTOS() {
        return cargoDTOS;
    }

    public void setCargoDTOS(List<CargoDTO> cargoDTOS) {
        this.cargoDTOS = cargoDTOS;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVesselName() {
        return vesselName;
    }

    public void setVesselName(String vesselName) {
        this.vesselName = vesselName;
    }

    public int getYearOfBuild() {
        return yearOfBuild;
    }

    public void setYearOfBuild(int yearOfBuild) {
        this.yearOfBuild = yearOfBuild;
    }

    @Override
    public String toString() {
        return "VesselDTO{" +
                "id=" + id +
                ", vesselName='" + vesselName + '\'' +
                ", yearOfBuild=" + yearOfBuild +
                ", cargoDTOS=" + cargoDTOS +
                '}';
    }
}
