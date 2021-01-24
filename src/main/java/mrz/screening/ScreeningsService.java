package mrz.screening;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Service
public class ScreeningsService {

    @Autowired
    ScreeningRepository screeningRepository;

    public List<ScreeningsDto> getScreeningsBetween(ZonedDateTime dateFrom, ZonedDateTime dateTo) {
        Map<Movie, List<Screening>> screeningsPerMovie = screeningRepository.findByDateBetween(dateFrom, dateTo)
                .stream()
                .collect(groupingBy(Screening::getMovie));

        return screeningsPerMovie
                .entrySet()
                .stream()
                .map(entry -> ScreeningsDto.from(entry.getKey(), entry.getValue()))
                .sorted(Comparator.comparing(ScreeningsDto::getMovieTitle))
                .collect(Collectors.toList());
    }
}