package com.project.feedback.controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import springfox.documentation.annotations.ApiIgnore;

@Controller
@RequiredArgsConstructor
@ApiIgnore
public class HomeController {

    @GetMapping(value = {"", "/", "/home"})
    public String home() {
        return "home";
    }
}
