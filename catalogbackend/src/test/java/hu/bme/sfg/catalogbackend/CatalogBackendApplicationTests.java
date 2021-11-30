package hu.bme.sfg.catalogbackend;

import hu.bme.sfg.catalogbackend.application.dto.PictureDto;
import hu.bme.sfg.catalogbackend.application.dto.RegisterDto;
import hu.bme.sfg.catalogbackend.application.dto.UserDto;
import hu.bme.sfg.catalogbackend.application.service.PictureHandlerService;
import hu.bme.sfg.catalogbackend.application.service.UserService;
import hu.bme.sfg.catalogbackend.application.service.mapper.PictureMapper;
import hu.bme.sfg.catalogbackend.application.service.mapper.UserMapper;
import hu.bme.sfg.catalogbackend.domain.Comment;
import hu.bme.sfg.catalogbackend.domain.Picture;
import hu.bme.sfg.catalogbackend.domain.User;
import hu.bme.sfg.catalogbackend.repository.CommentRepository;
import hu.bme.sfg.catalogbackend.repository.PictureRepositroy;
import hu.bme.sfg.catalogbackend.repository.UserRepository;
import hu.bme.sfg.catalogbackend.util.PictureException;
import hu.bme.sfg.catalogbackend.util.UserException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@SpringBootTest
class CatalogBackendApplicationTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PictureRepositroy pictureRepositroy;

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PictureHandlerService pictureHandlerService;
    private PictureMapper pictureMapper;
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserMapper userMapper;
    private UserDto userAnneDto;
    private User userAnne;

    @BeforeEach
    void init() {
        userAnne = User.builder()
                .email("anneeee@email.hu")
                .userName("anneeeee")
                .password(passwordEncoder.encode("passworddd"))
                .build();
        userAnne = userRepository.save(userAnne);
        userAnneDto = userMapper.userToUserDto(userAnne);
    }
    @Test
    void getAllPicturesTest() {
        Picture p1 = Picture.builder()
                .name("P1")
                .content(null)
                .user(userAnne)
                .build();
        Picture p2 = Picture.builder()
                .name("P2")
                .content(null)
                .user(userAnne)
                .build();
        Picture p3 = Picture.builder()
                .name("P3")
                .content(null)
                .user(userAnne)
                .build();

        pictureRepositroy.saveAll(Arrays.asList(p1, p2, p3));
        List<PictureDto> picturesDto = pictureHandlerService.getAllPictures();
        // A kezdeti adatok miatt 7 lesz helyes mert 4 et m'r betettünk az adatbázisba a CatalogBackendApplication
        Assertions.assertEquals(7, picturesDto.size());
    }

    @Test
    void getAllPicturesWithNameFilterTest() {
        Picture p1 = Picture.builder()
                .name("P112")
                .content(null)
                .user(userAnne)
                .build();
        Picture p2 = Picture.builder()
                .name("P113")
                .content(null)
                .user(userAnne)
                .build();
        Picture p3 = Picture.builder()
                .name("P224")
                .content(null)
                .user(userAnne)
                .build();
        pictureRepositroy.saveAll(Arrays.asList(p1, p2, p3));
        List<PictureDto> picturesDto = pictureHandlerService.getAllPicturesWithNameFilter("P11");
        // A kezdeti adatok miatt 7 lesz helyes mert 4 et m'r betettünk az adatbázisba a CatalogBackendApplication
        Assertions.assertEquals(2, picturesDto.size());
    }

    @Test
    void createPictureTest() {
        PictureDto p1 = PictureDto.builder()
                .name("P112")
                .content(null)
                .user(userAnneDto)
                .build();
        pictureRepositroy.save(pictureMapper.pictureDtoToPicture(p1));
// TODO finish
    }

    @Test
    void postCommentTest() {
        PictureDto p1 = PictureDto.builder()
                .name("P112")
                .content(null)
                .user(userAnneDto)
                .build();
        pictureRepositroy.save(pictureMapper.pictureDtoToPicture(p1));

        Comment c1 = Comment.builder()
                .picture(pictureMapper.pictureDtoToPicture(p1))
                .comment("P1 comment")
                .user(userAnne)
                .build();
        // TODO What is Principal ???
//        Principal principal = Principal();
//        pictureHandlerService.postComment(p1,c1,principal);
    }

    @Test
    void deletePictureTest() {
        Picture p1 = Picture.builder()
                .name("P112")
                .content(null)
                .user(userAnne)
                .build();
        pictureRepositroy.save(p1);
        Optional<Picture> pictureBeforeDelete = pictureRepositroy.findById(p1.getId());
        Assertions.assertTrue(pictureBeforeDelete.isPresent());

        try {
            pictureHandlerService.deletePicture(p1.getId());
            Optional<Picture> pictureAfterDelete = pictureRepositroy.findById(p1.getId());
            Assertions.assertFalse(pictureAfterDelete.isPresent());
        } catch (PictureException e) {
            e.printStackTrace();
        }
    }

    //user services
    @Test
    void registerTest() {
        try {
            RegisterDto registerDto = RegisterDto.builder()
                    .email("peter@email.hu")
                    .userName("peter")
                    .password(passwordEncoder.encode("asdadad"))
                    .build();
            UserDto userDto = userService.register(registerDto);
            Assertions.assertEquals(registerDto.getEmail(), userDto.getEmail());
            Assertions.assertEquals(registerDto.getUserName(), userDto.getUserName());
            Optional<User> user = userRepository.findByUserName(userDto.getUserName());
            Assertions.assertTrue(user.isPresent());
        } catch (UserException e) {
            e.printStackTrace();
        }

    }


}
