package com.Jose.AulaPos.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.Set;

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


    // EXERCISE 1

    @GetMapping("/{nome}")
    @ResponseBody
    public String retornaNome(@PathVariable String nome) {
        return "Olá, " + nome+ "!";
    }

    @GetMapping("/calc/soma/{a}/{b}")
    @ResponseBody
    public Map<String, Integer> calcularSoma(@PathVariable Integer a, @PathVariable Integer b) {
        if (a == 0 || b == 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Os valores de a e b devem ser números inteiros maiores que zero."
            );
        }

        Integer soma = a + b;

        return Map.of("resultado", soma);
    }

    @GetMapping("/temperatura/convert/{valor}/{de}/{para}")
    @ResponseBody
    public Map<String, Integer> converteTemperatura(@PathVariable Integer valor, @PathVariable String de, @PathVariable String para) {
        Set<String> unidadesValidas = Set.of("C", "F", "K");

        de = de.toUpperCase();
        para = para.toUpperCase();

        if (!unidadesValidas.contains(de) || !unidadesValidas.contains(para)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Unidade inválida. Use apenas C, F ou K."
            );
        }

        var resultConversao = resultConversao(de, para, valor);

        return Map.of("resultado", resultConversao);
    }

    private Integer resultConversao(String de, String para, Integer valor) {
        String conversao = de + "-" + para;

        return switch (conversao) {
            case "C-C", "F-F", "K-K" -> valor;

            case "C-F" -> (valor * 9 / 5) + 32;
            case "F-C" -> (valor - 32) * 5 / 9;

            case "C-K" -> valor + 273;
            case "K-C" -> valor - 273;

            case "F-K" -> ((valor - 32) * 5 / 9) + 273;
            case "K-F" -> ((valor - 273) * 9 / 5) + 32;

            default -> throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Conversão inválida."
            );
        };
    }
}
