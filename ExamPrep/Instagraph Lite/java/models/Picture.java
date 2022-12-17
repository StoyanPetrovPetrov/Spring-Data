package softuni.exam.instagraphlite.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "pictures")
public class Picture extends BaseEntity{
    @Column(nullable = false, unique = true)
    private String path;

    @Column(nullable = false)
    private double size;

    @OneToMany(targetEntity = User.class, mappedBy = "profilePicture")
    private List<User> users;

    @OneToMany(targetEntity = Post.class, mappedBy = "picture")
    private List<Post> posts;

    @Override
    public String toString() {
        return String.format("%.2f – %s",
                this.size,
                this.path);
    }
}
