package softuni.exam.models.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity(name = "tasks")
public class Task extends BaseEntity {

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private LocalDateTime date;

    @ManyToOne
    private Mechanic mechanic;

    @ManyToOne
    private Car car;

    @ManyToOne
    private Part part;
}
