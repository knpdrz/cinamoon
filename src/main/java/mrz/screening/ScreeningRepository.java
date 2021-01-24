package mrz.screening;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface ScreeningRepository extends JpaRepository<Screening, Long> {

    @Query(value = "from Screening where date between :dateFrom and :dateTo order by movie.title, date")
    List<Screening> findByDateBetween(@Param("dateFrom") ZonedDateTime dateFrom, @Param("dateTo") ZonedDateTime dateTo);
}
