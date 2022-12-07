package softuni.exam.models.dto;

import java.math.BigDecimal;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
public class TaskSeedDTO {

    @NotNull
    private String date;

    @NotNull
    @Positive
    private BigDecimal price;

    @NotNull
    private CarXmlSeedDTO car;

    @NotNull
    private MechanicXmlSeedDTO mechanic;

    @NotNull
    private PartXmlSeedDTO part;
}
