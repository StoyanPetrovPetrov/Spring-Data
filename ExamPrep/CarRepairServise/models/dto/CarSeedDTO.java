package softuni.exam.models.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import softuni.exam.models.enums.CarType;

@NoArgsConstructor
@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
public class CarSeedDTO {

    @NotNull
    @Length(min = 2, max = 30)
    private String carMake;

    @NotNull
    @Length(min = 2, max = 30)
    private String carModel;

    @NotNull
    @Positive
    private Integer year;

    @NotNull
    @Length(min = 2, max = 30)
    private String plateNumber;

    @NotNull
    @Positive
    private Integer kilometers;

    @NotNull
    @Min(1)
    private Double engine;

    @NotNull
    private CarType carType;
}
