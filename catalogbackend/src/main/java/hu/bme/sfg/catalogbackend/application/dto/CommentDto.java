package hu.bme.sfg.catalogbackend.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class CommentDto {

    @JsonProperty("id")
    private Long id;

    @NotBlank
    @NotEmpty
    @JsonProperty("picture_id")
    private Long pictureId;

    @NotBlank
    @NotEmpty
    @JsonProperty("user_id")
    private Long userId;

    @NotEmpty
    @NotBlank
    private String comment;
}
