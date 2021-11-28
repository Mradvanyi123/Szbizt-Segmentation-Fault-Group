package hu.bme.sfg.catalogbackend.application.service;

import hu.bme.sfg.catalogbackend.application.dto.CommentDto;
import hu.bme.sfg.catalogbackend.application.dto.PictureDto;
import hu.bme.sfg.catalogbackend.application.service.mapper.CommentMapper;
import hu.bme.sfg.catalogbackend.application.service.mapper.PictureMapper;
import hu.bme.sfg.catalogbackend.domain.Comment;
import hu.bme.sfg.catalogbackend.domain.Picture;
import hu.bme.sfg.catalogbackend.domain.User;
import hu.bme.sfg.catalogbackend.repository.CommentRepository;
import hu.bme.sfg.catalogbackend.repository.PictureRepositroy;
import hu.bme.sfg.catalogbackend.repository.UserRepository;
import hu.bme.sfg.catalogbackend.util.PictureException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class PictureHandlerServiceImpl implements PictureHandlerService {

    private PictureMapper pictureMapper;

    private CommentMapper commentMapper;

    private PictureRepositroy pictureRepositroy;

    private CommentRepository commentRepository;

    private UserRepository userRepository;

    @Override
    public List<PictureDto> getAllPictures() {
        log.info("Pictures are listed");
        return pictureRepositroy.findAll().stream().map(picture -> pictureMapper.pictureToPictureDto(picture)).collect(Collectors.toList());
    }

    @Override
    public List<PictureDto> getAllPicturesWithNameFilter(String name) {
        log.info("Pictures are filtered and listed");
        return pictureRepositroy.findByNameContaining(name).stream().map(picture -> pictureMapper.pictureToPictureDto(picture)).collect(Collectors.toList());
    }

    @Override
    public PictureDto getPicture(UUID pictureId) throws PictureException {
        Optional<Picture> picture = pictureRepositroy.findById(pictureId);
        if (picture.isPresent()) {
            return pictureMapper.pictureToPictureDto(picture.get());
        } else {
            log.error("Picture doesn't exists");
            throw new PictureException("Picture doesn't exists");
        }
    }

    @Override
    public PictureDto createPicture(PictureDto pictureDto) throws PictureException {
        //Picture newPicture = pictureMapper.pictureDtoToPicture(pictureDto);
        User creator = userRepository.findByUserName(pictureDto.getUser().getUserName()).get(); //TODO auth-ra kicserélni
        byte[] picture = getPicture(pictureDto.getContent()); //TODO CaffParser

        Picture newPicture = Picture.builder()
                .name(pictureDto.getName())
                .content(picture) //TODO CaffParser
                .user(creator)
                .build();
        pictureRepositroy.save(newPicture);

        creator.addPicture(newPicture);

        log.info("Picture is created");
        return pictureMapper.pictureToPictureDto(newPicture);
    }

    @Override
    public CommentDto postComment(UUID pictureId, CommentDto commentDto) throws PictureException {
        Optional<Picture> picture = pictureRepositroy.findById(pictureId);
        User creator = userRepository.findByUserName(commentDto.getUser().getUserName()).get(); //TODO auth-ra kicserélni

        if (picture.isPresent()) {
            Comment newComment = Comment.builder()
                    .comment(commentDto.getComment())
                    .picture(picture.get())
                    .user(creator)
                    .build();

            commentRepository.save(newComment);
            picture.get().addComment(newComment);
            creator.addComment(newComment);

            log.info("Comment is created");
            return commentMapper.commentToCommentDto(newComment);
        } else {
            log.error("Picture doesn't exists");
            throw new PictureException("Picture doesn't exists");
        }
    }

    @Override
    public void deletePicture(UUID pictureId) throws PictureException {
        if (pictureRepositroy.findById(pictureId).isPresent()) {
            pictureRepositroy.deleteById(pictureId);
            log.info("Picture is deleted");
        } else {
            log.error("Picture doesn't exists");
            throw new PictureException("Picture doesn't exists");
        }
    }

    public byte[] getPicture(byte[] pictureContent) {
        log.info("CAFF file is parsed");
        return null;
    }
}
