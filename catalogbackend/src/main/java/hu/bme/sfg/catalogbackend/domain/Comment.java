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
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long pictureId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String comment;

    private Comment(Comment.CommentBuilder commentBuilder) {
        this.pictureId = commentBuilder.pictureId;
        this.userId = commentBuilder.userId;
        this.comment = commentBuilder.comment;
    }

    public static class CommentBuilder {

        public Comment build() {
            return new Comment(this);
        }
    }
}
