package com.exchange.app.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterRequest {

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String fullName;

}
