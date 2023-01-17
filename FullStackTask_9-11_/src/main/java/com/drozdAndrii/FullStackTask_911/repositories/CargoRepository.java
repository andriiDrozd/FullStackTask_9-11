package com.drozdAndrii.FullStackTask_911.repositories;

import com.drozdAndrii.FullStackTask_911.dto.CargoDTO;
import com.drozdAndrii.FullStackTask_911.model.Cargo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.net.ContentHandler;
import java.util.List;

@Repository
public interface CargoRepository extends JpaRepository<Cargo,Integer> {

//    Cargo findByCargoNameAndSendNumber(String cargoName, int sendNumber, PageRequest pageRequest);
    Cargo findBySendNumber(int sendNumber);

    List<Cargo> findByCargoNameAndVesselID(String cargoName, int vesselId,PageRequest pageRequest);



}
