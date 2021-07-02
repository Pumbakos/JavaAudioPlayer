package pl.pumbakos.japwebservice.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class WebService {

    public static void main(String[] args) {
        SpringApplication.run(WebService.class, args);
    }

}

