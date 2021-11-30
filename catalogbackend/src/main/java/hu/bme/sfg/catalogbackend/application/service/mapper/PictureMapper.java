package hu.bme.sfg.catalogbackend.application.service.mapper;

import hu.bme.sfg.catalogbackend.application.dto.PictureDto;
import hu.bme.sfg.catalogbackend.domain.Picture;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PictureMapper {
    PictureDto pictureToPictureDto(Picture picture);

    Picture pictureDtoToPicture(PictureDto pictureDto);
}
