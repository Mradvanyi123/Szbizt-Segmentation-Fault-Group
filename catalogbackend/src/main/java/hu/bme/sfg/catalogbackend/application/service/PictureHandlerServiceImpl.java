package hu.bme.sfg.catalogbackend.application.service;

import hu.bme.sfg.catalogbackend.application.dto.CommentDto;
import hu.bme.sfg.catalogbackend.application.dto.PictureDto;
import hu.bme.sfg.catalogbackend.application.service.mapper.UserMapper;
import hu.bme.sfg.catalogbackend.util.PictureException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class PictureHandlerServiceImpl implements PictureHandlerService {

    private UserMapper userMapper;

    @Override
    public List<PictureDto> getAllPictures() {
        return null;
    }

    @Override
    public List<PictureDto> getAllPicturesWithNameFilter(String name) {
        return null;
    }

    @Override
    public PictureDto createPicture(PictureDto pictureDto) throws PictureException {
        return null;
    }

    @Override
    public CommentDto postComment(Long pictureId, CommentDto commentDto) throws PictureException {
        return null;
    }

    @Override
    public Boolean deletePicture(Long pictureId) throws PictureException {
        return null;
    }
}
