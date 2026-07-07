package divar.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test")

public class TestController {

    @GetMapping("/secure")
    public String secureTest() {
        return "You are authenticated";
    }
}