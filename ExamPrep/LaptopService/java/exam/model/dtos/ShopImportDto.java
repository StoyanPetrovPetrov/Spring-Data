package exam.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "shop")
@XmlAccessorType(XmlAccessType.FIELD)
public class ShopImportDto {

    @XmlElement
    @Size(min = 4)
    @NotNull
    private String name;

    @XmlElement
    @Min(2000)
    @NotNull
    private double income;

    @XmlElement
    @Size(min = 4)
    @NotNull
    private String address;

    @XmlElement(name = "employee-count")
    @Min(1)
    @Max(50)
    @NotNull
    private int employeeCount;

    @XmlElement(name = "shop_area")
    @Min(150)
    @NotNull
    private int shopArea;

    @XmlElement(name = "town")
    @NotNull
    private NameDto town;
}
