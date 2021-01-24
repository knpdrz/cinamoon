package mrz;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MrzController {

    @RequestMapping("/icecream")
    @ResponseBody
    String getIceCream() {
        return "elo mordo";
    }
}