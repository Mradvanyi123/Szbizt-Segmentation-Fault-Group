package hu.bme.sfg.catalogbackend.application.service;

import hu.bme.sfg.catalogbackend.application.dto.CommentDto;
import hu.bme.sfg.catalogbackend.application.dto.PictureDto;
import hu.bme.sfg.catalogbackend.application.service.mapper.PictureMapper;
import hu.bme.sfg.catalogbackend.repository.PictureRepositroy;
import hu.bme.sfg.catalogbackend.util.PictureException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class PictureHandlerServiceImpl implements PictureHandlerService {

    private PictureMapper pictureMapper;

    private PictureRepositroy pictureRepositroy;

    @Override
    public List<PictureDto> getAllPictures() {
        return pictureRepositroy.findAll().stream().map(picture -> pictureMapper.pictureToPictureDto(picture)).collect(Collectors.toList());
    }

    @Override
    public List<PictureDto> getAllPicturesWithNameFilter(String name) {
        return pictureRepositroy.findByNameContaining(name).stream().map(picture -> pictureMapper.pictureToPictureDto(picture)).collect(Collectors.toList());
    }

    @Override
    public PictureDto createPicture(PictureDto pictureDto) throws PictureException {
        return null;
    }

    @Override
    public CommentDto postComment(UUID pictureId, CommentDto commentDto) throws PictureException {
        return null;
    }

    @Override
    public void deletePicture(UUID pictureId) throws PictureException {
        if (pictureRepositroy.findById(pictureId).isPresent()) {
            pictureRepositroy.deleteById(pictureId);
        } else {
            log.error("Picture doesn't exists");
            throw new PictureException("Picture doesn't exists");
        }
    }
}
