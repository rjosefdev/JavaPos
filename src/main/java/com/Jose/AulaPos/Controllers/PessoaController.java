package com.Jose.AulaPos.Controllers;

import com.Jose.AulaPos.Models.Pessoa;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

    @PutMapping("/pessoa/{id}")
    @ResponseBody
    public Pessoa substituirPessoa(@PathVariable Long id, @RequestBody Pessoa novaPessoa) {
        Pessoa pessoa = pessoas.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Pessoa não encontrada."
                ));

        pessoa.setId(novaPessoa.getId());
        pessoa.setNome(novaPessoa.getNome());

        return pessoa;
    }

    @PatchMapping("/pessoa/{id}")
    @ResponseBody
    public Pessoa atualizarPessoa(@PathVariable Long id, @RequestBody Pessoa dadosAtualizacao) {
        Pessoa pessoa = pessoas.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Pessoa não encontrada."
                ));

        if (dadosAtualizacao.getNome() != null) {
            pessoa.setNome(dadosAtualizacao.getNome());
        }

        if (dadosAtualizacao.getId() != null) {
            pessoa.setId(dadosAtualizacao.getId());
        }

        return pessoa;
    }

    @DeleteMapping("/pessoa/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarPessoa(@PathVariable Long id) {
        Pessoa pessoa = pessoas.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Pessoa não encontrada."
                ));

        pessoas.remove(pessoa);
    }
}
