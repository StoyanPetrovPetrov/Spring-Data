package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ForecastImportDto;
import softuni.exam.models.dto.ForecastsWrapperDto;
import softuni.exam.models.entity.City;
import softuni.exam.models.entity.Forecast;
import softuni.exam.models.entity.enums.DayOfWeek;
import softuni.exam.repository.CityRepository;
import softuni.exam.repository.ForecastRepository;
import softuni.exam.service.ForecastService;
import softuni.exam.util.ValidationUtils;
import softuni.exam.util.XmlParser;


import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import static softuni.exam.config.Messages.INVALID_FORECAST;
import static softuni.exam.constants.Paths.FORECASTS_PATH;

@Service
public class ForecastServiceImpl implements ForecastService {
    public final String PRINT_FORMAT = "City: %s:%n -min temperature: %.2f%n --max temperature: %.2f%n ---sunrise: %s:%n ----sunset: %s:%n";

private final CityRepository cityRepository;
    private final ForecastRepository forecastRepository;
    private final Gson gson;
    private final ValidationUtils validationUtils;

    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;

    @Autowired
    public ForecastServiceImpl(CityRepository cityRepository, ForecastRepository forecastRepository, Gson gson, ValidationUtils validationUtils, ModelMapper modelMapper, XmlParser xmlParser) {
        this.cityRepository = cityRepository;
        this.forecastRepository = forecastRepository;
        this.gson = gson;
        this.validationUtils = validationUtils;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean areImported() {
        return this.forecastRepository.count()>0;
    }

    @Override
    public String readForecastsFromFile() throws IOException {
        return Files.readString(FORECASTS_PATH);
    }

    @Override
    public String importForecasts() throws IOException, JAXBException {
        final StringBuilder stringBuilder = new StringBuilder();

        final File file = FORECASTS_PATH.toFile();

        final ForecastsWrapperDto forecastsWrapperDto =
                xmlParser.fromFile(file, ForecastsWrapperDto.class);

        final List<ForecastImportDto> forecasts = forecastsWrapperDto.getForecasts();

        for (ForecastImportDto forecast : forecasts) {
            boolean isValid = validationUtils.isValid(forecast);

            if (isValid) {
                if (cityRepository.findFirstById(forecast.getCity()).isPresent()) {
                    final City refCity = cityRepository.findFirstById(forecast.getCity()).get();

                    final Forecast forecastToSave = this.modelMapper.map(forecast, Forecast.class);

                    forecastToSave.setCity(refCity);
//                    forecastToSave.setSunrise(LocalTime.parse(forecast.getSunrise(),
//                            DateTimeFormatter.ofPattern("HH:mm:ss")));
//                    forecastToSave.setSunset(LocalTime.parse(forecast.getSunset(),
//                            DateTimeFormatter.ofPattern("HH:mm:ss")));

                    this.forecastRepository.saveAndFlush(forecastToSave);
                    stringBuilder.append(INVALID_FORECAST).append(System.lineSeparator());
                }
                continue;
            }

            stringBuilder.append(INVALID_FORECAST).append(System.lineSeparator());
        }

        return stringBuilder.toString();
    }

    @Override
    public String exportForecasts() {

        final Set<Forecast> forecasts = this.forecastRepository
                .findAllByDayOfWeekAndCity_PopulationLessThanOrderByMaxTemperatureDescIdAsc(DayOfWeek.SUNDAY, 150000L)
                .orElseThrow(NoSuchElementException::new);


        return forecasts.stream()
                .map(forecast -> String.format(PRINT_FORMAT,
                        forecast.getCity().getCityName(),
                        forecast.getMinTemperature(),
                        forecast.getMaxTemperature(),
                        forecast.getSunrise(),
                        forecast.getSunset()))
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
