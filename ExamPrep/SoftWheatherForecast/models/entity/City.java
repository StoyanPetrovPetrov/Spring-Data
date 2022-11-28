package softuni.exam.models.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "cities")
public class City extends BaseEntity{

    @Size(min = 2,max = 60)
    @Column(unique = true,nullable = false)
    private String cityName;

   @Size(min = 2)
    @Column(columnDefinition = "TEXT")
    private String description;

    @Min(value = 500)
    @Column(nullable = false)
    private long population;

    @ManyToOne
    private Country country;
}
