package softuni.exam.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class ApplicationBeanConfiguration {
    @Bean
    public Gson gson(){

        return new GsonBuilder().setPrettyPrinting().create();
    }

    @Bean
    public ModelMapper modelMapper(){

        final ModelMapper modelMapper = new ModelMapper();

        modelMapper.addConverter(mappingContext ->
                        LocalTime.parse(mappingContext.getSource(), DateTimeFormatter.ofPattern("HH:mm:ss")),
                String.class,
                LocalTime.class);

        return modelMapper;

    }

}
