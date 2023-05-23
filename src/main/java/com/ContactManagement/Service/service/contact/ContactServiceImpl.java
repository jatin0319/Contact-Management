package com.ContactManagement.Service.service.contact;

import com.ContactManagement.Service.dao.ContactRepository;
import com.ContactManagement.Service.dto.ContactDetailsDto;
import com.ContactManagement.Service.models.ActiveStatus;
import com.ContactManagement.Service.dto.CreateContactRequestDto;
import com.ContactManagement.Service.dto.CreateContactResponseDto;
import com.ContactManagement.Service.dto.DeleteContactResponseDto;
import com.ContactManagement.Service.dto.UpdateContactRequestDto;
import com.ContactManagement.Service.dto.UpdateContactResponseDto;
import com.ContactManagement.Service.models.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;

    @Autowired
    public ContactServiceImpl(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public CreateContactResponseDto saveContact(CreateContactRequestDto createContactRequest, String operation) {
        Contact contact = contactRepository.findByPhoneNumberAndActiveStatus(createContactRequest.getPhoneNumber(), ActiveStatus.active());
        if (contact != null)
            throw new RuntimeException("Contact already exist with this number");

        contact = Contact.builder()
                .firstName(createContactRequest.getFirstName())
                .lastName(createContactRequest.getLastName())
                .email(createContactRequest.getEmail())
                .phoneNumber(createContactRequest.getPhoneNumber())
                .activeStatus(ActiveStatus.active())
                .build();

        contactRepository.save(contact);
        return CreateContactResponseDto.builder()
                .message("Contact saved successfully")
                .build();
    }

    @Override
    public DeleteContactResponseDto deleteContact(String mobileNumber, String operation) {
        Contact contact = contactRepository.findByPhoneNumberAndActiveStatus(mobileNumber, ActiveStatus.active());
        if (contact == null)
            throw new RuntimeException("Contact not found, Please try again");

        contact = contact.toBuilder()
                .activeStatus(ActiveStatus.inactive())
                .build();
        contactRepository.save(contact);

        return DeleteContactResponseDto.builder()
                .message("Contact deleted successfully")
                .build();
    }

    @Override
    public List<ContactDetailsDto> getContactList(Integer pageNumber, Integer pageSize, String search, String sort,
                                                  String sortDir, String operation) {

        this.validateQueryParameter(pageNumber, pageSize, search, sortDir);

        List<ContactDetailsDto> contactDetailsList = new ArrayList<>();
        Sort sortBy = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sort).ascending() : Sort.by(sort).descending() ;

        Pageable pagination = PageRequest.of(pageNumber, pageSize, sortBy);
        List<Contact> contactList =
                contactRepository.findByFirstNameContainingOrLastNameContainingOrEmailContainingAndActiveStatus(search, search, search,ActiveStatus.active(), pagination);
        contactList.forEach(contact -> {
            contactDetailsList.add(ContactDetailsDto.builder()
                            .firstName(contact.getFirstName())
                            .lastName(contact.getLastName())
                            .email(contact.getEmail())
                            .phoneNumber(contact.getPhoneNumber())
                    .build());
        });

        return contactDetailsList;
    }

    private void validateQueryParameter(Integer pageNumber, Integer pageSize, String search, String sortDir){
        if (pageNumber <0 || pageNumber >999)
            throw new RuntimeException("Invalid page number");

        if (pageSize <0 || pageSize >999)
            throw new RuntimeException("Invalid page size");

        if (!sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) && !sortDir.equalsIgnoreCase(Sort.Direction.DESC.name()) )
            throw new RuntimeException("Invalid sort direction");
    }

    @Override
    public UpdateContactResponseDto updateContactDetails(String mobileNumber, UpdateContactRequestDto updateContactRequest, String operation) {
        Contact contact = contactRepository.findByPhoneNumberAndActiveStatus(mobileNumber, ActiveStatus.active());
        if (contact == null)
            throw new RuntimeException("Contact not found, Please try again");

        contact = contact.toBuilder()
                .firstName(updateContactRequest.getFirstName())
                .lastName(updateContactRequest.getLastName())
                .email(updateContactRequest.getEmail())
                .phoneNumber(updateContactRequest.getPhoneNumber())
                .build();
        contactRepository.save(contact);

        return UpdateContactResponseDto.builder()
                .message("Contact Updated successfully")
                .build();
    }
}
