package hu.bme.sfg.catalogbackend.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Comment {

    @Id
    private UUID id = UUID.randomUUID();

    @ManyToOne
    private Picture picture;

    @ManyToOne
    private User user;

    @Column(nullable = false)
    private String comment;

    private Comment(Comment.CommentBuilder commentBuilder) {
        this.picture = commentBuilder.picture;
        this.user = commentBuilder.user;
        this.comment = commentBuilder.comment;
    }

    public static class CommentBuilder {

        public Comment build() {
            return new Comment(this);
        }
    }
}
