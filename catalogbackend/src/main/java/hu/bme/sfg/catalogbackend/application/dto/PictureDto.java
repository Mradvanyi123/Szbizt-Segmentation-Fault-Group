package hu.bme.sfg.catalogbackend.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class PictureDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("user_name")
    private String userName;

    @NotBlank
    @NotEmpty
    @JsonProperty("picture_name")
    private String pictureName;

    private byte[] content;
}
