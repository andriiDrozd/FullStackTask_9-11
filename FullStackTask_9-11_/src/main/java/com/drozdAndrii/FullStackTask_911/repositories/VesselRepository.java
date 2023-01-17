package com.drozdAndrii.FullStackTask_911.repositories;

import com.drozdAndrii.FullStackTask_911.model.Vessel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VesselRepository extends JpaRepository<Vessel,Integer> {
}
