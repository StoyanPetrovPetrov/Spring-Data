package softuni.exam.models.dto;

import lombok.*;
import softuni.exam.models.entity.enums.DayOfWeek;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "forecast")
@XmlAccessorType(XmlAccessType.FIELD)
public class ForecastImportDto {

    @NotNull
    @XmlElement(name = "day_of_week")
    private DayOfWeek dayOfWeek;

    @DecimalMax(value = "40")
    @DecimalMin(value = "-50")
    @NotNull
    @XmlElement(name = "min_temperature")
    private Double minTemperature;

    @DecimalMax(value = "60")
    @DecimalMin(value = "-20")
    @NotNull
    @XmlElement(name = "max_temperature")
    private Double maxTemperature;

    @NotNull
    @XmlElement
    private String sunset;

    @NotNull
    @XmlElement
    private String sunrise;

    @NotNull
    @XmlElement
    private Long city;
}
