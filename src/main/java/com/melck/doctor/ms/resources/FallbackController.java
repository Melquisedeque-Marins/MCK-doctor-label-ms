package com.melck.doctor.ms.resources;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

    @GetMapping("/doctorLabel")
    public ResponseEntity<String> doctorLabelFallback () {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("it is a internam proccessament problem in order API. try again later");
    }

    @GetMapping("/label")
    public ResponseEntity<String> labelFallback () {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("it is a internam proccessament problem in payment API. try again later");
    }
    
}
