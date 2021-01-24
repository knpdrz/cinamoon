package mrz.screening;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
public class ScreeningDto {

    private long id;
    private ZonedDateTime date;
    private long roomId;

    static ScreeningDto from(Screening screening) {
        return new ScreeningDto(screening.getId(), screening.getDate(), screening.getRoomId());
    }
}
