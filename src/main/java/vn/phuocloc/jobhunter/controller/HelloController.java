package vn.phuocloc.jobhunter.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.phuocloc.jobhunter.service.error.IdInvalidException;

@RestController
public class HelloController {

    @GetMapping("/")
    public String getHelloWorld() throws IdInvalidException {
        if (true)
            throw new IdInvalidException("check mate PhuocLoc");
        return "Hello World PhuocLoc";
    }

}
