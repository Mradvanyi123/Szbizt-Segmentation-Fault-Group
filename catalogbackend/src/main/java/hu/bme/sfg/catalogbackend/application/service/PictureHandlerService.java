package hu.bme.sfg.catalogbackend.application.service;

import hu.bme.sfg.catalogbackend.application.dto.CommentDto;
import hu.bme.sfg.catalogbackend.application.dto.PictureDto;
import hu.bme.sfg.catalogbackend.util.PictureException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.text.ParseException;
import java.util.List;
import java.util.UUID;

public interface PictureHandlerService {
    List<PictureDto> getAllPictures();

    List<PictureDto> getAllPicturesWithNameFilter(String name);

    PictureDto getPicture(UUID pictureId) throws PictureException;

    PictureDto createPicture(String fileName, MultipartFile file, Principal principal) throws PictureException, ParseException, IOException;

    PictureDto editPictureName(String newName, UUID pictureId) throws PictureException;

    CommentDto postComment(UUID pictureId, CommentDto commentDto, Principal principal) throws PictureException;

    void deletePicture(UUID pictureId) throws PictureException;
}
