package hu.bme.sfg.catalogbackend.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.UUID;

@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class PictureDto {

    @JsonProperty("id")
    private UUID id;

    @NotBlank
    @NotEmpty
    @JsonProperty("name")
    private String name;

    @JsonProperty("user")
    private UserDto user;

    @JsonProperty("content")
    private byte[] content;

    @JsonProperty("comments")
    List<CommentDto> comments;
}
