package com.utils.AppCommonUtils.models.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequest {
    @Email(message = "inValid Email Address")
    private String emailAddress;
    @NotBlank(message = "password cannot be Empty")
    @Size(min = 8, message = "password cannot be lesser than 8 characters")
    private String password;
}