package hu.bme.sfg.catalogbackend.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

public class RegisterDto extends UserDto {
    @NotBlank
    @NotEmpty(message = "password must not be blank")
    @JsonProperty("password")
    private String password;
}
