package mrz.screening;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
public class ScreeningsServiceTest {

    @org.springframework.boot.test.context.TestConfiguration
    static class TestConfiguration {

        @Bean
        public ScreeningsService screeningsService() {
            return new ScreeningsService();
        }
    }

    @MockBean
    ScreeningRepository screeningRepository;

    @Autowired
    ScreeningsService screeningsService;

    @Test
    public void testSth() {
        ZonedDateTime date = ZonedDateTime.now();
        Movie movie1 = new Movie(null, "abc", 69, 1995);
        Movie movie2 = new Movie(null, "aabc", 222, 1999);

        List<Screening> dummyScreenings = Arrays.asList(
                new Screening(null, date, 18L, movie1),
                new Screening(null, date.minusDays(20), 18L, movie1),
                new Screening(null, date, 18L, movie2),
                new Screening(null, date.plusDays(30), 18L, movie2));

        when(screeningRepository.findByDateBetween(any(ZonedDateTime.class), any(ZonedDateTime.class)))
                .thenReturn(dummyScreenings);

        List<ScreeningsDto> screeningsDtos = screeningsService.getScreeningsBetween(date, date);

        List<ScreeningsDto> sortedScreeningsDtos = Arrays.asList(
                new ScreeningsDto(movie2.getTitle(), movie2.getProductionYear(), movie2.getDurationInMinutes(),
                        Arrays.asList(new ScreeningDto(date, 18L),
                                new ScreeningDto(date.plusDays(30), 18L))),
                new ScreeningsDto(movie1.getTitle(), movie1.getProductionYear(), movie1.getDurationInMinutes(),
                        Arrays.asList(new ScreeningDto(date.minusDays(20), 18L),
                                new ScreeningDto(date, 18L))));

        assertThat(screeningsDtos).size().isEqualTo(2);
        assertThat(screeningsDtos).isEqualTo(sortedScreeningsDtos);
    }
}