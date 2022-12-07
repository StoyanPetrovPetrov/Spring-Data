package softuni.exam.models.dto;

import com.google.gson.annotations.Expose;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@NoArgsConstructor
@Getter
@Setter
public class PartSeedDTO {

    @Expose
    @NotNull
    @Length(min = 2, max = 19)
    private String partName;

    @Expose
    @NotNull
    @Min(10)
    @Max(2000)
    private Double price;

    @Expose
    @NotNull
    @Positive
    private Integer quantity;
}
