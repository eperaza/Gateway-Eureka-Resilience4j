package com.example.end2.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping(path = "/end2")
public class Controller {

    @GetMapping(path = "/message")
    public ResponseEntity<Object> responseMessage(){
		String response = "Hello from endpoint 2";
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}
    
}
