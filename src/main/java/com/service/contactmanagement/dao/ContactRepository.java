package com.service.contactmanagement.dao;

import com.service.contactmanagement.models.ActiveStatus;
import com.service.contactmanagement.models.Contact;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer> {

    Contact findByPhoneNumberAndActiveStatus(String mobileNumber, ActiveStatus activeStatus);

    List<Contact> findByFirstNameContainingOrLastNameContainingOrEmailContainingAndActiveStatus(String firstName, String LastName,
                                                                      String email, ActiveStatus activeStatus, Pageable pageable);

}
