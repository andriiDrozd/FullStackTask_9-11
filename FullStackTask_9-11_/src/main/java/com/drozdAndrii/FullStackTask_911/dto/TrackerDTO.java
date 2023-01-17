package com.drozdAndrii.FullStackTask_911.dto;

import com.drozdAndrii.FullStackTask_911.model.Vessel;

public class TrackerDTO {
    private int id;
    private String vesselName;
    private int yearOfBuild;

    public TrackerDTO() {
    }

    public TrackerDTO(String vesselName, int yearOfBuild) {
        this.vesselName = vesselName;
        this.yearOfBuild = yearOfBuild;
    }

    public TrackerDTO(int id, String vesselName, int yearOfBuild) {
        this.id = id;
        this.vesselName = vesselName;
        this.yearOfBuild = yearOfBuild;
    }

    public static TrackerDTO from(Vessel vessel){
        TrackerDTO trackerDTO =new TrackerDTO();
        trackerDTO.setId(vessel.getId());
        trackerDTO.setVesselName(vessel.getVesselName());
        trackerDTO.setYearOfBuild(vessel.getYearOfBuild());
        return trackerDTO;
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
}
