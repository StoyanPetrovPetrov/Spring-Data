package softuni.exam.models.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.exam.models.enums.CarType;

@NoArgsConstructor
@Getter
@Setter
@Entity(name = "cars")
public class Car extends BaseEntity {

    @Column(name = "car_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private CarType carType;

    @Column(name = "car_make", nullable = false)
    private String carMake;

    @Column(name = "car_model", nullable = false)
    private String carModel;

    @Column(nullable = false)
    private Integer year;

    @Column(name = "plate_number", nullable = false, unique = true)
    private String plateNumber;

    @Column(nullable = false)
    private Integer kilometers;

    @Column(nullable = false)
    private Double engine;
}
