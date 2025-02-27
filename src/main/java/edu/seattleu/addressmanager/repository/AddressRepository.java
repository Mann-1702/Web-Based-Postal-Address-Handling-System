package edu.seattleu.addressmanager.repository;

import edu.seattleu.addressmanager.model.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    List<Address> findByCity_Id(Long cityId);

    List<Address> findByCity_State_Id(Long stateId);

    @Query(value = """
        SELECT a FROM Address a 
        JOIN FETCH a.city c 
        JOIN FETCH c.state s 
        JOIN FETCH s.country co 
        WHERE LOWER(a.address01) LIKE LOWER(CONCAT('%', :street, '%'))
        """,
        countQuery = """
        SELECT COUNT(a) FROM Address a 
        JOIN a.city c 
        JOIN c.state s 
        JOIN s.country co 
        WHERE LOWER(a.address01) LIKE LOWER(CONCAT('%', :street, '%'))
        """)
    Page<Address> findByStreetContainingIgnoreCase(@Param("street") String street, Pageable pageable);

    @Query(value = """
           SELECT a
           FROM Address a
           JOIN FETCH a.city c
           JOIN FETCH c.state s
           JOIN FETCH s.country co
           WHERE (:address01 IS NULL OR :address01 = ''  OR LOWER(a.address01) LIKE CONCAT('%', LOWER(:address01), '%'))
           AND (:address02 IS NULL OR :address02 = '' OR LOWER(a.address02) LIKE CONCAT('%', LOWER(:address02), '%'))
           AND (:postalCode IS NULL OR :postalCode = '' OR LOWER(a.postalCode) LIKE CONCAT('%', LOWER(:postalCode), '%'))
           AND (:cityId IS NULL OR c.id = :cityId) 
           AND (:stateId IS NULL OR s.id = :stateId) 
           AND (:countryId IS NULL OR co.id = :countryId)
           """,
            countQuery = """
           SELECT COUNT(a)
           FROM Address a
           JOIN a.city c
           JOIN c.state s
           JOIN s.country co
           WHERE (:address01 IS NULL OR :address01 = ''  OR LOWER(a.address01) LIKE CONCAT('%', LOWER(:address01), '%'))
           AND (:address02 IS NULL OR :address02 = '' OR LOWER(a.address02) LIKE CONCAT('%', LOWER(:address02), '%'))
           AND (:postalCode IS NULL OR :postalCode = '' OR LOWER(a.postalCode) LIKE CONCAT('%', LOWER(:postalCode), '%'))
           AND (:cityId IS NULL OR c.id = :cityId) 
           AND (:stateId IS NULL OR s.id = :stateId) 
           AND (:countryId IS NULL OR co.id = :countryId)
           """)
    Page<Address> searchAddresses(
            @Param("address01") String address01,
            @Param("address02") String address02,
            @Param("postalCode") String postalCode,
            @Param("cityId") Long cityId,
            @Param("stateId") Long stateId,
            @Param("countryId") Long countryId,
            Pageable pageable
    );


}
