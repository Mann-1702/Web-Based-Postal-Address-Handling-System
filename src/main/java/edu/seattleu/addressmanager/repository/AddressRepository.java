package edu.seattleu.addressmanager.repository;

import edu.seattleu.addressmanager.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    List<Address> findByCity_Id(Long cityId);

    List<Address> findByCity_State_Id(Long stateId);

    @Query("SELECT a FROM Address a " +
            "JOIN FETCH a.city c " +
            "JOIN FETCH c.state s " +
            "JOIN FETCH s.country co " +
            "WHERE LOWER(a.address01) LIKE LOWER(CONCAT('%', :street, '%'))")
    List<Address> findByStreetContainingIgnoreCase(@Param("street") String street);
}
