package exam.config;

import com.google.gson.*;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.Validation;
import javax.validation.Validator;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Configuration
public class ApplicationBeanConfiguration {
    @Bean
    public ModelMapper createModelMapper() {
        return new ModelMapper();
    }

    @Bean
    public Gson createGson() {

        final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        JsonDeserializer<LocalDate> toLocalDate =
                (json, t, c) -> LocalDate.parse(json.getAsString(), dateFormat);

        JsonSerializer<String> fromLocalDate =
                (date, t, c) -> new JsonPrimitive(date);

        return new GsonBuilder()
                .setPrettyPrinting()
                .serializeNulls()
                .registerTypeAdapter(LocalDate.class, toLocalDate)
                .registerTypeAdapter(LocalDate.class, fromLocalDate)
                .create();
    }

    @Bean
    public Validator createValidation() {
        return Validation.buildDefaultValidatorFactory().getValidator();
    }


}
