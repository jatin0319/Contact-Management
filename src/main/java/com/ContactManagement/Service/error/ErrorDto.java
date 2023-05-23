package com.ContactManagement.Service.error;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class ErrorDto {
    @JsonProperty("message")
    private String message;

}
