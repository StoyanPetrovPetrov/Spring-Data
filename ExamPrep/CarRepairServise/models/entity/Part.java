package softuni.exam.models.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity(name = "parts")
public class Part extends BaseEntity {

    @Column(name = "part_name", nullable = false, unique = true)
    private String partName;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Integer quantity;
}
