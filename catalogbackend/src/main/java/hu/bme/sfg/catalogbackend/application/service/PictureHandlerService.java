package hu.bme.sfg.catalogbackend.application.service;

import hu.bme.sfg.catalogbackend.application.dto.CommentDto;
import hu.bme.sfg.catalogbackend.application.dto.PictureDto;
import hu.bme.sfg.catalogbackend.util.PictureException;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

public interface PictureHandlerService {
    List<PictureDto> getAllPictures();

    List<PictureDto> getAllPicturesWithNameFilter(String name);

    PictureDto getPicture(UUID pictureId) throws PictureException;

    PictureDto createPicture(PictureDto pictureDto, Principal principal) throws PictureException;

    PictureDto editPictureName(String newName, UUID pictureId) throws PictureException;

    CommentDto postComment(UUID pictureId, CommentDto commentDto, Principal principal) throws PictureException;

    void deletePicture(UUID pictureId) throws PictureException;
}
