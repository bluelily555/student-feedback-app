package com.project.feedback.controller.api;

import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
//import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/boards")
@Api(tags = {"글작성"})
public class WriteApiController {

}
