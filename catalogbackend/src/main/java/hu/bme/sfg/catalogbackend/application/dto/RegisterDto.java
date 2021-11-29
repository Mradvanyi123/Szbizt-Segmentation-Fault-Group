package hu.bme.sfg.catalogbackend.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

//@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class RegisterDto extends UserDto {
    @NotBlank
    @NotEmpty(message = "password must not be blank")
    @JsonProperty("password")
    private String password;
}
