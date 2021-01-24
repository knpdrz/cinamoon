package mrz.screening;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Clock;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class ScreeningRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ScreeningRepository screeningRepository;

    @Test
    public void zzzzzz() {
        Movie movie1 = new Movie(null, "Potop", 69, 1995);
        Movie movie2 = new Movie(null, "Ogniem i mieczem", 222, 1999);

        ZoneOffset offset = ZonedDateTime.now().getOffset();
        ZonedDateTime date = ZonedDateTime.of(2021, 1, 1, 12, 0, 0, 0, offset);

        Screening screening1 = new Screening(null, date, 1L, movie1);
        Screening screening2 = new Screening(null, date.plusHours(3), 2L, movie1);
        Screening screening3 = new Screening(null, date, 2L, movie2);
        Screening screening4 = new Screening(null, date, 1L, movie2);

        entityManager.persist(movie1);
        entityManager.persist(movie2);
        entityManager.persist(screening1);
        entityManager.persist(screening2);
        entityManager.persist(screening3);
        entityManager.persist(screening4);

        entityManager.flush();

        List<Screening> screenings = screeningRepository.findByDateBetween(date.minusDays(1), date.plusDays(2));

        System.out.println("printing all");
        System.out.println(screenings);
    }
}