package com.drozdAndrii.FullStackTask_911.dto;

import com.drozdAndrii.FullStackTask_911.model.Cargo;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.Objects;

public class CargoDTO {
private int id;
    @NotEmpty(message = "Cargo name should not be empty")
    @Size(min=2,max=100, message ="Cargo name should be between 2 and 100 characters")
    private String cargoName;
    @NotNull
    @Max(value = 2147483645, message = "Send number should be uniq and less than 2147483645")
    @Min(value = 0,message = "Send number should be uniq and greater than 0")
    private int sendNumber;
    private TrackerDTO trackerDTO;

    public static CargoDTO from(Cargo cargo){
        CargoDTO cargoDTO=new CargoDTO();
        cargoDTO.setId(cargo.getId());
        cargoDTO.setCargoName(cargo.getCargoName());
        cargoDTO.setSendNumber(cargo.getSendNumber());
        if(Objects.nonNull(cargo.getTrucker())){
            cargoDTO.setTrackerDTO(TrackerDTO.from(cargo.getTrucker()));
        }
        return cargoDTO;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TrackerDTO getTrackerDTO() {
        return trackerDTO;
    }

    public void setTrackerDTO(TrackerDTO trackerDTO) {
        this.trackerDTO = trackerDTO;
    }


    public String getCargoName() {
        return cargoName;
    }

    public void setCargoName(String cargoName) {
        this.cargoName = cargoName;
    }

    public int getSendNumber() {
        return sendNumber;
    }

    public void setSendNumber(int sendNumber) {
        this.sendNumber = sendNumber;
    }
}
