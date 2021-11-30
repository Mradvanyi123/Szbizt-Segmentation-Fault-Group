package hu.bme.sfg.catalogbackend;

import hu.bme.sfg.catalogbackend.application.dto.CommentDto;
import hu.bme.sfg.catalogbackend.application.dto.PictureDto;
import hu.bme.sfg.catalogbackend.application.dto.RegisterDto;
import hu.bme.sfg.catalogbackend.application.dto.UserDto;
import hu.bme.sfg.catalogbackend.application.service.PictureHandlerService;
import hu.bme.sfg.catalogbackend.application.service.UserService;
import hu.bme.sfg.catalogbackend.application.service.mapper.PictureMapper;
import hu.bme.sfg.catalogbackend.application.service.mapper.UserMapper;
import hu.bme.sfg.catalogbackend.domain.Picture;
import hu.bme.sfg.catalogbackend.domain.User;
import hu.bme.sfg.catalogbackend.repository.CommentRepository;
import hu.bme.sfg.catalogbackend.repository.PictureRepositroy;
import hu.bme.sfg.catalogbackend.repository.UserRepository;
import hu.bme.sfg.catalogbackend.util.PictureException;
import hu.bme.sfg.catalogbackend.util.UserException;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@RunWith(SpringRunner.class)
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

    @Autowired
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

    @AfterEach
    void after() {
        commentRepository.deleteAll();
        pictureRepositroy.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void getAllPicturesTest() {
        Picture p1 = Picture.builder()
                .name("P1")
                .content(new byte[]{})
                .user(userAnne)
                .build();
        Picture p2 = Picture.builder()
                .name("P2")
                .content(new byte[]{})
                .user(userAnne)
                .build();
        Picture p3 = Picture.builder()
                .name("P3")
                .content(new byte[]{})
                .user(userAnne)
                .build();

        pictureRepositroy.saveAll(Arrays.asList(p1, p2, p3));
        List<PictureDto> picturesDto = pictureHandlerService.getAllPictures();
        Assertions.assertEquals(3, picturesDto.size());
    }

    @Test
    void getAllPicturesWithNameFilterTest() {
        Picture p1 = Picture.builder()
                .name("P112")
                .content(new byte[]{})
                .user(userAnne)
                .build();
        Picture p2 = Picture.builder()
                .name("P113")
                .content(new byte[]{})
                .user(userAnne)
                .build();
        Picture p3 = Picture.builder()
                .name("P224")
                .content(new byte[]{})
                .user(userAnne)
                .build();
        pictureRepositroy.saveAll(Arrays.asList(p1, p2, p3));
        List<PictureDto> picturesDto = pictureHandlerService.getAllPicturesWithNameFilter("P11");
        Assertions.assertEquals(2, picturesDto.size());
    }

    @Test
    void createPictureTest() throws IOException, ParseException, PictureException {

        String path = "src/test/java/hu/bme/sfg/catalogbackend/testcaff/1.caff";
        File file = new File(path);
        InputStream is = new FileInputStream(file);
        byte[] fileBytes = is.readAllBytes();
        MockMultipartFile multipartFile = new MockMultipartFile("1", "1.caff", null, fileBytes);

        Principal mockPrincipal = Mockito.mock(Principal.class);
        Mockito.when(mockPrincipal.getName()).thenReturn("anneeeee");

        PictureDto picture = pictureHandlerService.createPicture("Ez egy teszt kép",multipartFile, mockPrincipal);

        Assert.assertNotNull(picture);
    }

    @Test
    void postCommentTest() throws PictureException {
        Picture picture = Picture.builder()
                .name("P112")
                .content(new byte[]{})
                .user(userAnne)
                .build();

        pictureRepositroy.save(picture);

        CommentDto commentDto = CommentDto.builder()
                .comment("P1 comment")
                .user(userAnneDto)
                .build();

        Principal mockPrincipal = Mockito.mock(Principal.class);
        Mockito.when(mockPrincipal.getName()).thenReturn("anneeeee");

        CommentDto comment = pictureHandlerService.postComment(picture.getId(), commentDto, mockPrincipal);
    }

    @Test
    void deletePictureTest() {
        Picture p1 = Picture.builder()
                .name("P112")
                .content(new byte[]{})
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

    @Test
    void registerTest() {
        try {
            RegisterDto registerDto = RegisterDto.builder()
                    .email("peterTest@email.hu")
                    .userName("peterTest")
                    .password(passwordEncoder.encode("thisisapassword"))
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
