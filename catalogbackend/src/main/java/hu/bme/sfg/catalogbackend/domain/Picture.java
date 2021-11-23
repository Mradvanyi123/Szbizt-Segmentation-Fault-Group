package hu.bme.sfg.catalogbackend.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Picture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String pictureName;

    @Column(nullable = false)
    @Lob
    private byte[] content;

    private String userName;

    private Picture(Picture.PictureBuilder pictureBuilder) {
        this.userName = pictureBuilder.userName;
        this.pictureName = pictureBuilder.pictureName;
        this.content = pictureBuilder.content;
    }

    public static class PictureBuilder {

        public Picture build() {
            return new Picture(this);
        }
    }
}
