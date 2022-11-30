package softuni.exam.models.dto;

import lombok.*;
import softuni.exam.util.LocalDateAdapter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "offer")
@XmlAccessorType(XmlAccessType.FIELD)
public class OfferImportDto {

    @NonNull
    @Positive
    @XmlElement
    private BigDecimal price;

    @XmlElement
    @NotNull
    private AgentNameDto agent;

    @XmlElement
    @NotNull
    private ApartmentIdDto apartment;

    @XmlElement
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    @NotNull
    private LocalDate publishedOn;


}
