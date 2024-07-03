package vn.phuocloc.jobhunter.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")
    public String getHelloWorld() {
        return "Xin chao nguyen phuoc loc";
    }
}
