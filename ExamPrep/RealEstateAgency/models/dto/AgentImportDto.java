package softuni.exam.models.dto;

import com.google.gson.annotations.SerializedName;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AgentImportDto {

    @Size(min = 2)
    @NonNull
    private String firstName;

    @Size(min = 2)
    @NonNull
    private String lastName;

    @Email
    @NonNull
    private String email;

    @NonNull
    @SerializedName("town")
    private String townName;
}
