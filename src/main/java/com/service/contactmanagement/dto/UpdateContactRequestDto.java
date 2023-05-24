package com.service.contactmanagement.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class UpdateContactRequestDto {

    @Pattern(regexp = "^[A-Za-z][A-Za-z0-9_]{3,40}", message = "Invalid first name")
    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    @Pattern(regexp = "^[A-Za-z][A-Za-z0-9_]{3,40}", message = "Invalid last name")
    private String lastName;

    @Pattern(regexp = "^[6-9]\\d{9}$", message = "Invalid phone number")
    @JsonProperty("phoneNumber")
    private String phoneNumber;

    @Pattern(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$", message = "Invalid email Id")
    @JsonProperty("email")
    private String email;
}
