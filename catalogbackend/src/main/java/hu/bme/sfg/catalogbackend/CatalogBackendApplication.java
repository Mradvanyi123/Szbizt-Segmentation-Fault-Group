package hu.bme.sfg.catalogbackend;

import hu.bme.sfg.catalogbackend.domain.Comment;
import hu.bme.sfg.catalogbackend.domain.Picture;
import hu.bme.sfg.catalogbackend.domain.User;
import hu.bme.sfg.catalogbackend.repository.CommentRepository;
import hu.bme.sfg.catalogbackend.repository.PictureRepositroy;
import hu.bme.sfg.catalogbackend.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
@Slf4j
@AllArgsConstructor
public class CatalogBackendApplication implements CommandLineRunner {

    private UserRepository userRepository;

    private PictureRepositroy pictureRepositroy;

    private CommentRepository commentRepository;

    public static void main(String[] args) {
        SpringApplication.run(CatalogBackendApplication.class, args);
        log.info("CatalogBackendApplication is successfully started");
    }

    @Override
    public void run(String... args) throws Exception {
        //Test data
        User userAnne = User.builder()
                .email("anne@email.hu")
                .userName("anne")
                .build();
        User userPeter = User.builder()
                .email("peter@email.hu")
                .userName("peter")
                .build();

        userRepository.saveAll(Arrays.asList(userAnne, userPeter));

        Picture pictureFlower = Picture.builder()
                .name("Lila virágok")
                .content(null)
                .user(userAnne)
                .build();
        Picture pictureDog = Picture.builder()
                .name("Egy shiba inu kutya")
                .content(null)
                .user(userAnne)
                .build();
        Picture pictureSky = Picture.builder()
                .name("Kék ég")
                .content(null)
                .user(userPeter)
                .build();
        Picture pictureGrass = Picture.builder()
                .name("Zöld fű")
                .content(null)
                .build();

        pictureRepositroy.saveAll(Arrays.asList(pictureFlower, pictureDog, pictureSky, pictureGrass));

        Comment commentDog = Comment.builder()
                .picture(pictureDog)
                .comment("Nagyon aranyos")
                .user(userPeter)
                .build();
        Comment commentSky1 = Comment.builder()
                .picture(pictureSky)
                .comment("Jó kék az ég")
                .user(userAnne)
                .build();
        Comment commentSky2 = Comment.builder()
                .picture(pictureSky)
                .comment("Ugye, szerintem is nagyon szép")
                .user(userPeter)
                .build();

        commentRepository.saveAll(Arrays.asList(commentDog, commentSky1, commentSky2));
    }

}
