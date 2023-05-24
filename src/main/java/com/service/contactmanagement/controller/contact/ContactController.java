package com.service.contactmanagement.controller.contact;

import com.service.contactmanagement.dto.ContactDetailsDto;
import com.service.contactmanagement.dto.CreateContactRequestDto;
import com.service.contactmanagement.dto.CreateContactResponseDto;
import com.service.contactmanagement.dto.DeleteContactResponseDto;
import com.service.contactmanagement.dto.UpdateContactRequestDto;
import com.service.contactmanagement.dto.UpdateContactResponseDto;
import com.service.contactmanagement.service.contact.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/contact")
public class ContactController {

    private static final String SAVE_CONTACT_DETAILS = "save-contact-details";
    private static final String FETCH_CONTACT_DETAILS_LIST = "fetch-contact-details-list";
    private static final String DELETE_CONTACT_DETAILS = "delete-contact-details";
    private static final String UPDATE_CONTACT_DETAILS = "update-contact-details";
    private final ContactService contactService;

    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @RequestMapping(value = "/import", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<CreateContactResponseDto> createContact(@Valid @RequestBody CreateContactRequestDto createContactRequestDto) {
        return new ResponseEntity<>(contactService.saveContact(createContactRequestDto, SAVE_CONTACT_DETAILS), HttpStatus.OK);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<ContactDetailsDto>> getContactDetailsList(@RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
                                                                         @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                                                         @RequestParam(value = "sort", defaultValue = "firstName", required = false) String sort,
                                                                         @RequestParam(value = "search", defaultValue = "", required = false) String search,
                                                                         @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
        List<ContactDetailsDto> contactList =
                contactService.getContactList(pageNumber, pageSize, search, sort, sortDir, FETCH_CONTACT_DETAILS_LIST);
        return new ResponseEntity<>(contactList, HttpStatus.OK);
    }

    @RequestMapping(value = "/{mobileNumber}", method = RequestMethod.PATCH)
    public ResponseEntity<UpdateContactResponseDto> getContactDetailsList(@PathVariable(value = "mobileNumber") String mobileNumber,
                                                                          @Valid @RequestBody UpdateContactRequestDto updateContactRequest) {
        UpdateContactResponseDto updateContactResponse =
                contactService.updateContactDetails(mobileNumber, updateContactRequest, UPDATE_CONTACT_DETAILS);
        return new ResponseEntity<>(updateContactResponse, HttpStatus.OK);
    }

    @RequestMapping(value = "/{mobileNumber}", method = RequestMethod.DELETE)
    public ResponseEntity<DeleteContactResponseDto> deleteContact(@PathVariable(name = "mobileNumber") String mobileNumber) {
        return new ResponseEntity<>(contactService.deleteContact(mobileNumber, DELETE_CONTACT_DETAILS), HttpStatus.OK);
    }
}
