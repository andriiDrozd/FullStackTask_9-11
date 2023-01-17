package com.drozdAndrii.FullStackTask_911.model;

import com.drozdAndrii.FullStackTask_911.dto.CargoDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "Cargo")
public class Cargo {
    @Id
    @Column(name = "cargo_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "cargo_name")
    @NotEmpty(message = "Cargo name should not be empty")
    @Size(min=2,max=100, message ="Cargo name should be between 2 and 100 characters")
    private String cargoName;
    @NotNull
    @Max(value = Integer.MAX_VALUE, message = "Send number should be uniq and less than 2147483647")

    @Column(name = "send_number")
    private int sendNumber;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "vessel_id", referencedColumnName = "id")
    @JsonBackReference
    private Vessel trucker;

    public Cargo() {
    }

    public Cargo(String cargoName, int sendNumber) {
        this.cargoName = cargoName;
        this.sendNumber = sendNumber;
    }

    public Cargo(String cargoName, int sendNumber, Vessel trucker) {
        this.cargoName = cargoName;
        this.sendNumber = sendNumber;
        this.trucker = trucker;
    }

    public static Cargo from(CargoDTO cargoDTO){
        Cargo cargo=new Cargo();
        cargo.setCargoName(cargoDTO.getCargoName());
        cargo.setSendNumber(cargoDTO.getSendNumber());
        return cargo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Vessel getTrucker() {
        return trucker;
    }

    public void setTrucker(Vessel trucker) {
        this.trucker = trucker;
    }

    @Override
    public String toString() {
        return "Cargo{" +
                "id=" + id +
                ", cargoName='" + cargoName + '\'' +
                ", sendNumber=" + sendNumber +
                ", trucker=" + trucker +
                '}';
    }
}
