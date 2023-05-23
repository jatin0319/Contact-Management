package com.ContactManagement.Service.service.contact;

import com.ContactManagement.Service.dto.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ContactService {

    CreateContactResponseDto saveContact(CreateContactRequestDto createContactRequest, String operation);

    DeleteContactResponseDto deleteContact(String mobileNumber, String operation);

    List<ContactDetailsDto> getContactList(Integer pageNumber, Integer pageSize, String search, String sort,
                                           String sortDir, String operation);

    UpdateContactResponseDto updateContactDetails(String mobileNumber, UpdateContactRequestDto updateContactRequest, String operation);
}
