package hu.bme.sfg.catalogbackend.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class UserDto {

    @JsonProperty("id")
    private UUID id;

    @NotBlank
    @NotEmpty(message = "username must not be blank")
    @JsonProperty("username")
    private String userName;

    @Email
    @NotEmpty
    @NotBlank
    private String email;

    @JsonProperty("role")
    private String role;
}
