package hu.bme.sfg.catalogbackend.application.service;

import hu.bme.sfg.catalogbackend.application.dto.CommentDto;
import hu.bme.sfg.catalogbackend.application.dto.PictureDto;
import hu.bme.sfg.catalogbackend.util.PictureException;

import java.util.List;

public interface PictureHandlerService {
    List<PictureDto> getAllPictures();

    List<PictureDto> getAllPicturesWithNameFilter(String name);

    PictureDto createPicture(PictureDto pictureDto) throws PictureException;

    CommentDto postComment(Long pictureId, CommentDto commentDto) throws PictureException;

    Boolean deletePicture(Long pictureId) throws PictureException;
}
