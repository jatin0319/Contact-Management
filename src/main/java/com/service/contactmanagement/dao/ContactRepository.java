package com.service.contactmanagement.dao;

import com.service.contactmanagement.models.ActiveStatus;
import com.service.contactmanagement.models.Contact;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer> {

    Contact findByPhoneNumberAndActiveStatus(String mobileNumber, ActiveStatus activeStatus);

    @Query(value = "select con from Contact con where con.activeStatus= ?1 and (con.firstName LIKE %?2% or " +
            "con.lastName LIKE %?2% or con.email LIKE %?2%)")
    List<Contact> findByActiveStatus(ActiveStatus activeStatus, String search, Pageable pageable);

}
