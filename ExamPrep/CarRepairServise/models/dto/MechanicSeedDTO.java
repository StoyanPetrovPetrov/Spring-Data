package softuni.exam.models.dto;

import com.google.gson.annotations.Expose;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@NoArgsConstructor
@Getter
@Setter
public class MechanicSeedDTO {

    @Expose
    @NotNull
    @Email
    private String email;

    @Expose
    @NotNull
    @Length(min = 2)
    private String firstName;

    @Expose
    @NotNull
    @Length(min = 2)
    private String lastName;

    @Expose
    @Length(min = 2)
    private String phone;
}
