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
@Table(name = "\"user\"")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String userName;

    @Column(nullable = false, unique = true)
    private String email;


    private User(UserBuilder userBuilder) {
        this.userName = userBuilder.userName;
        this.email = userBuilder.email;
    }

    public static class UserBuilder {

        public User build() {
            return new User(this);
        }
    }
}
