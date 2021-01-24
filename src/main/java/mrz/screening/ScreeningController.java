package mrz.screening;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;
import java.util.List;

@RestController
@Slf4j
public class ScreeningController {

    @Autowired
    ScreeningsService screeningsService;

    @GetMapping("/screenings")
    public List<ScreeningsDto> getScreeningsBetween(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime dateFrom,
                                                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime dateTo) {
        log.info("getting screeinngs between {} and {}", dateFrom, dateTo);
        return screeningsService.getScreeningsBetween(dateFrom, dateTo);
    }

}
