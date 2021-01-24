package mrz.screening;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class ScreeningController {

    @Autowired
    ScreeningsService screeningsService;

    private final static Logger LOG = Logger.getLogger(ScreeningController.class.getName());

    @GetMapping("/screenings")
    public List<ScreeningsDto> getScreeningsBetween(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime dateFrom,
                                                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime dateTo) {
        LOG.log(Level.INFO, "getting screeinngs between " + dateFrom + " and " + dateTo);
        return screeningsService.getScreeningsBetween(dateFrom, dateTo);
    }

}
