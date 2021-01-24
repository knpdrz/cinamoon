package mrz.screening;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class ScreeningsDto {

    private String movieTitle;
    private int movieProductionYear;
    private int movieDurationInMinutes;
    private List<ScreeningDto> screenings;

    static ScreeningsDto from(Movie movie, List<Screening> screenings) {
        List<ScreeningDto> screeningDtos = screenings
                .stream()
                .map(ScreeningDto::from)
                .sorted(Comparator.comparing(ScreeningDto::getDate))
                .collect(Collectors.toList());
        return new ScreeningsDto(movie.getTitle(), movie.getProductionYear(), movie.getDurationInMinutes(),
                screeningDtos);
    }
}