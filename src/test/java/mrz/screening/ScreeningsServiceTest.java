package mrz.screening;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
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
    public void shouldReturnSortedScreeningsInGivenPeriod() {
        //given
        ZonedDateTime date = ZonedDateTime.now();
        Movie movie1 = new Movie(null, "abc", 69, 1995);
        Movie movie2 = new Movie(null, "aabc", 222, 1999);

        List<Screening> dummyScreenings = Arrays.asList(
                new Screening(1L, date, 18L, movie1),
                new Screening(2L, date.minusHours(1), 18L, movie1),
                new Screening(3L, date, 18L, movie2),
                new Screening(4L, date.plusHours(2), 18L, movie2));

        when(screeningRepository.findByDateBetween(any(ZonedDateTime.class), any(ZonedDateTime.class)))
                .thenReturn(dummyScreenings);

        //when
        List<ScreeningsDto> screeningsDtos = screeningsService.getScreeningsBetween(date.minusHours(2), date.plusHours(2));

        //assert
        List<ScreeningsDto> sortedScreeningsDtos = Arrays.asList(
                new ScreeningsDto(movie2.getTitle(), movie2.getProductionYear(), movie2.getDurationInMinutes(),
                        Arrays.asList(new ScreeningDto(3L, date, 18L),
                                new ScreeningDto(4L, date.plusHours(2), 18L))),
                new ScreeningsDto(movie1.getTitle(), movie1.getProductionYear(), movie1.getDurationInMinutes(),
                        Arrays.asList(new ScreeningDto(2L, date.minusHours(1), 18L),
                                new ScreeningDto(1L, date, 18L))));

        assertThat(screeningsDtos).isEqualTo(sortedScreeningsDtos);
    }
}