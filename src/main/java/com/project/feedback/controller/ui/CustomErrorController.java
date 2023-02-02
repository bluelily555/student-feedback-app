package com.project.feedback.controller.ui;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.Map;


@Controller
public class CustomErrorController implements ErrorController {

    @ExceptionHandler(Throwable.class)
    @GetMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Throwable throwable = new Throwable();
        String trace = Arrays.toString(throwable.getStackTrace());
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        String code = status.toString();
        HttpStatus httpStatus = HttpStatus.valueOf(Integer.parseInt(code));

        model.addAttribute("trace", trace);
        model.addAttribute("code", code);
        model.addAttribute("msgPhrase", httpStatus.getReasonPhrase());
        return "error/error";
    }
}
