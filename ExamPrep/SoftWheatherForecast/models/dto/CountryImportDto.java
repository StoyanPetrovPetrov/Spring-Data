package softuni.exam.models.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class CountryImportDto {

    @Size(min = 2,max = 60)
    private String countryName;

    @Size(min = 2,max = 20)
    private String currency;
}
