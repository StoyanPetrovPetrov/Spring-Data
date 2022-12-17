package softuni.exam.instagraphlite.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends BaseEntity{
    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @ManyToOne
    private Picture profilePicture;

    @OneToMany(targetEntity = Post.class, mappedBy = "user")
    private List<Post> posts;

    @Override
    public String toString() {
        return String.format("User: %s\n" +
                        "Post count: %d\n" +
                        "%s",
                this.username,
                this.posts.size(),
                this.posts
                        .stream()
                        .sorted(Comparator.comparingDouble(p -> p.getPicture().getSize()))
                        .map(Post::toString)
                        .collect(Collectors.joining(System.lineSeparator())));
    }
}
