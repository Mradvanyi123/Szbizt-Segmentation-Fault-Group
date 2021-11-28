package hu.bme.sfg.catalogbackend.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class CommentDto {

    @JsonProperty("id")
    private UUID id;

    @NotBlank
    @NotEmpty
    @JsonProperty("picture_id")
    private UUID pictureId;

    @NotBlank
    @NotEmpty
    @JsonProperty("user_id")
    private UUID userId;

    @NotEmpty
    @NotBlank
    private String comment;
}
