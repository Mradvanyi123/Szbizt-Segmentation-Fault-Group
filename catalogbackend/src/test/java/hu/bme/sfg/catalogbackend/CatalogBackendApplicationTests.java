package hu.bme.sfg.catalogbackend;

import hu.bme.sfg.catalogbackend.application.dto.RegisterDto;
import hu.bme.sfg.catalogbackend.application.service.PictureHandlerService;
import hu.bme.sfg.catalogbackend.application.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class CatalogBackendApplicationTests {

    @Autowired
    private PictureHandlerService pictureHandlerService;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void getAllPicturesTest() {

    }

    @Test
    void getAllPicturesWithNameFilterTest() {
    }

    @Test
    void createPictureTest() {
    }

    @Test
    void postCommentTest() {
    }

    @Test
    void deletePictureTest() {
    }

    //user services
    @Test
    void registerTest() {
        RegisterDto registerDto = RegisterDto.builder()
                .email("peter@email.hu")
                .userName("peter")
                .password(passwordEncoder.encode("asdadad"))
                .build();
    }

    @Test
    void getMyInfoTest() {

    }

}
