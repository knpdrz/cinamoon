package mrz.screening;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.ZonedDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Screening {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private ZonedDateTime date;

    @Column
    private Long roomId;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

}
