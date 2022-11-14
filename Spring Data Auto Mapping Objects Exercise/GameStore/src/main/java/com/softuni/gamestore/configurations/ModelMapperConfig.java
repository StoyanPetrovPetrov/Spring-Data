package com.softuni.gamestore.configurations;

import com.softuni.gamestore.models.dtos.GameAddDto;
import com.softuni.gamestore.models.entities.Game;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        Converter<String, LocalDate> toDate =
                ctx -> LocalDate.parse(ctx.getSource(), formatter);

        mapper.createTypeMap(GameAddDto.class, Game.class)
                .addMappings(mpr ->
                        mpr.using(toDate).map(GameAddDto::getReleaseDate, Game::setReleaseDate));
        return mapper;
    }
}
