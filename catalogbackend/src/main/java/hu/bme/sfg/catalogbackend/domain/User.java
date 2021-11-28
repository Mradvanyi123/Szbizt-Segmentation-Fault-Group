package hu.bme.sfg.catalogbackend.domain;

import hu.bme.sfg.catalogbackend.util.Role;
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
@Table(name = "\"user\"")
public class User {

    @Id
    private UUID id = UUID.randomUUID();

    @Column(nullable = false, unique = true)
    private String userName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Role role;

    @OneToMany(mappedBy = "user", orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Picture> pictures;

    @OneToMany(mappedBy = "user", orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Comment> comments;

    private User(UserBuilder userBuilder) {
        this.userName = userBuilder.userName;
        this.email = userBuilder.email;
        this.password = userBuilder.password;
        this.role = userBuilder.role == null ? Role.USER : userBuilder.role;
        this.pictures = userBuilder.pictures;
        this.comments = userBuilder.comments;
    }

    public void addPicture(Picture picture) {
        if (this.pictures == null) {
            this.pictures = new ArrayList<>();
        }
        this.pictures.add(picture);
    }

    public void addComment(Comment comment) {
        if (this.comments == null) {
            this.comments = new ArrayList<>();
        }
        this.comments.add(comment);
    }

    public static class UserBuilder {

        public User build() {
            return new User(this);
        }
    }
}
