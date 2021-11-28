package hu.bme.sfg.catalogbackend.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Picture {

    @Id
    private UUID id = UUID.randomUUID();

    @Column(nullable = false)
    private String name;

    @Column(nullable = true) //TODO jav false-ra
    @Lob
    private byte[] content;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "picture", orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Comment> comments;

    private Picture(Picture.PictureBuilder pictureBuilder) {
        this.user = pictureBuilder.user;
        this.name = pictureBuilder.name;
        this.content = pictureBuilder.content;
        this.comments = pictureBuilder.comments;
    }

    public void addComment(Comment comment) {
        if (this.comments == null) {
            this.comments = new ArrayList<>();
        }
        this.comments.add(comment);
    }

    public static class PictureBuilder {

        public Picture build() {
            return new Picture(this);
        }
    }
}
