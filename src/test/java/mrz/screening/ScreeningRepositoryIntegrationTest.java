package mrz.screening;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
class ScreeningRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ScreeningRepository screeningRepository;

    private final ZonedDateTime today = ZonedDateTime.now().withHour(0).withMinute(0).withSecond(1);

    private final Movie movie1 = new Movie(null, "Potop", 69, 1995);
    private final Movie movie2 = new Movie(null, "Ogniem i mieczem", 222, 1999);

    Screening screening11 = new Screening(null, today, 11L, movie1);
    Screening screening12 = new Screening(null, today.plusHours(3), 12L, movie1);
    Screening screening13 = new Screening(null, today.plusHours(13), 13L, movie1);
    Screening screening14 = new Screening(null, today.plusDays(3), 14L, movie1);
    Screening screening15 = new Screening(null, today.minusHours(3), 15L, movie1);
    Screening screening16 = new Screening(null, today.minusDays(3), 16L, movie1);

    Screening screening21 = new Screening(null, today, 21L, movie2);
    Screening screening22 = new Screening(null, today.plusHours(7), 22L, movie2);
    Screening screening23 = new Screening(null, today.minusDays(1), 23L, movie2);
    Screening screening24 = new Screening(null, today.minusDays(7), 24L, movie2);

    @BeforeEach
    private void setup() {
        saveObjectsToDb(movie1, movie2);
        saveObjectsToDb(screening11, screening12, screening13, screening14, screening15, screening16,
                screening21, screening22, screening23, screening24);
    }

    @Test
    public void shouldReturnScreeningsBetweenNowAndTomorrow() {
        List<Screening> screenings = screeningRepository.findByDateBetween(today, today.plusDays(1));
        assertThat(screenings).hasSameElementsAs(Arrays.asList(screening11, screening12, screening13, screening21, screening22));
    }

    @Test
    public void shouldReturnScreeningsBetweenNowAndWeekFromNow() {
        List<Screening> screenings = screeningRepository.findByDateBetween(today, today.plusDays(7));
        assertThat(screenings).hasSameElementsAs(Arrays.asList(screening11, screening12, screening13, screening14,
                screening21, screening22));
    }

    @Test
    public void shouldReturnScreeningsBetweenWeekAgoAndToday() {
        List<Screening> screenings = screeningRepository.findByDateBetween(today.minusDays(7), today);
        assertThat(screenings).hasSameElementsAs(Arrays.asList(screening11, screening15, screening16,
                screening21, screening23, screening24));
    }

    @Test
    public void shouldReturnScreeningsBetweenNoonAndMidnight() {
        ZonedDateTime noon = today.plusHours(12);
        ZonedDateTime midnight = today.plusDays(1);
        List<Screening> screenings = screeningRepository.findByDateBetween(noon, midnight);
        assertThat(screenings).hasSameElementsAs(Collections.singletonList(screening13));
    }

    private void saveObjectsToDb(Object... movies) {
        Arrays.stream(movies).forEach(entityManager::persist);
        entityManager.flush();
    }
}