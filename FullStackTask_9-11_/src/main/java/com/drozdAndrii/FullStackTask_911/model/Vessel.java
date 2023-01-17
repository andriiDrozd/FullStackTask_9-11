package com.drozdAndrii.FullStackTask_911.model;

import com.drozdAndrii.FullStackTask_911.dto.VesselDTO;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "Vessel")
public class Vessel {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty(message = "Vessel name should not be empty")
    @Size(min = 2, max = 100, message = "Vessel name should be between 2 and 100 characters")
    @Column(name = "vessel_name")
    private String vesselName;
    @Min(value = 1800, message = "Year of built should be greater than 1800")
    @Max(value = 2024, message = "Year of built should be less than 2024")
    @Column(name = "year_of_build")
    private int yearOfBuild;

    @OneToMany(mappedBy = "trucker", fetch = FetchType.LAZY,cascade = CascadeType.REMOVE,
            orphanRemoval = true)
    @JsonManagedReference
    private List<Cargo> cargoes;

    public Vessel() {
    }

    public Vessel(String name, int yearOfBuild) {
        this.vesselName = name;
        this.yearOfBuild = yearOfBuild;
    }

    public Vessel(String vesselName, int yearOfBuild, List<Cargo> cargoes) {
        this.vesselName = vesselName;
        this.yearOfBuild = yearOfBuild;
        this.cargoes = cargoes;
    }

    public static Vessel from(VesselDTO vesselDTO){
        Vessel vessel=new Vessel();
        vessel.setVesselName(vesselDTO.getVesselName());
        vessel.setYearOfBuild(vesselDTO.getYearOfBuild());
        return vessel;
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

    public List<Cargo> getCargoes() {
        return cargoes;
    }

    public void setCargoes(List<Cargo> cargoes) {
        this.cargoes = cargoes;
    }

    @Override
    public String toString() {
        return "Vessel{" +
                "id=" + id +
                ", vesselName='" + vesselName + '\'' +
                ", yearOfBuild=" + yearOfBuild +
                '}';
    }
}
