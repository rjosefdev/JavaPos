package com.Jose.AulaPos.Controllers;

import com.Jose.AulaPos.Models.Pessoa;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/pessoa")
public class PessoaController {
    private static final List<Pessoa> pessoas = new ArrayList<>();

    @PostMapping("/pessoa")
    @ResponseBody
    public Pessoa criar(@RequestBody Pessoa novaPessoa) {
        pessoas.add(novaPessoa);
        return novaPessoa;
    }

    @GetMapping("/pessoas")
    @ResponseBody
    public List<Pessoa> listar() {
        return pessoas;
    }

    @GetMapping("/pessoa/{nome}")
    @ResponseBody
    public Optional<Pessoa> buscar(@PathVariable String nome) {
        return pessoas.stream()
                .filter(i -> i.getNome().equalsIgnoreCase(nome))
                .findFirst();
    }
}
