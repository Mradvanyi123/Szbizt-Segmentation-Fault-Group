package hu.bme.sfg.catalogbackend.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public final class UserDto {

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
}
