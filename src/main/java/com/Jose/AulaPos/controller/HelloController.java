package com.Jose.AulaPos.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/hello")
public class HelloController {
    @GetMapping("/hello")
    @ResponseBody
    public String hello() {
        return "HELLO World!";
    }

    @GetMapping("/world")
    @ResponseBody
    public String world() {
        return "Hello WORLD!";
    }

    @PostMapping("/world/{valor}")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public String retornaValor(@PathVariable String valor) {
        return valor;
    }
}
