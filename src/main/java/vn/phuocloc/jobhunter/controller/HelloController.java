package vn.phuocloc.jobhunter.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")
    public ResponseEntity<String> getHelloWorld() {
        return ResponseEntity.status(HttpStatus.OK).body("Xin chao nguyen phuoc loc");
    }
}
