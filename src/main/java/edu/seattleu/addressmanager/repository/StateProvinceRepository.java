package edu.seattleu.addressmanager.repository;

import edu.seattleu.addressmanager.model.StateProvince;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StateProvinceRepository extends JpaRepository<StateProvince, Long> {
    List<StateProvince> findByCountry_Id(Long countryId);
}
