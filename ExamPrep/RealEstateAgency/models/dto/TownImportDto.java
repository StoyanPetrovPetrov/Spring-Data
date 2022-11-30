package softuni.exam.models.dto;

import lombok.*;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TownImportDto {

    @Size(min = 2)
    @NonNull
    private String townName;

    @Positive
    private int population;
}
