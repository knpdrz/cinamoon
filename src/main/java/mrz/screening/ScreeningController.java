package mrz.screening;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;
import java.util.List;

@RestController
public class ScreeningController {

    @Autowired
    ScreeningsService screeningsService;

    private static final Logger LOG = LoggerFactory.getLogger(ScreeningController.class.getName());

    @GetMapping("/screenings")
    public List<ScreeningsDto> getScreeningsBetween(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime dateFrom,
                                                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime dateTo) {
        LOG.info("getting screeinngs between " + dateFrom + " and " + dateTo);
        return screeningsService.getScreeningsBetween(dateFrom, dateTo);
    }

}
