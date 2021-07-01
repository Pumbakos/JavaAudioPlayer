package pl.pumbakos.japwebservice.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // dana klasa stonowi interfejt REST
@SpringBootApplication
public class WebService {

    public static void main(String[] args) {
        SpringApplication.run(WebService.class, args);
    }

    @GetMapping("/seyHello")
    public String sayHello() {
        return "hello";
    }
}

