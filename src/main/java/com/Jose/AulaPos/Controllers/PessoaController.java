package com.Jose.AulaPos.Controllers;

import com.Jose.AulaPos.Models.Pessoa;
import com.Jose.AulaPos.Repositories.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {
    @Autowired
    private PessoaRepository pessoaRepository;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Pessoa adicionar(@RequestBody Pessoa p) {
        return pessoaRepository.saveAndFlush(p);
    }

    @GetMapping()
    public List<Pessoa> listar() {
        return pessoaRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Pessoa> buscarPorId(@PathVariable Long id) {
        try{
            return pessoaRepository.findById(id);
        } catch (Exception ex){
            return Optional.empty();
        }
    }

    @PutMapping("/{id}")
    public Pessoa alterar(@PathVariable Long id, @RequestBody Pessoa novaPessoa) {
        Pessoa p = pessoaRepository.findById(id)
                .orElseThrow(()-> new
                        ResponseStatusException(HttpStatus.NOT_FOUND, "Pessoa não encontrada!"));
        p.setNome(novaPessoa.getNome());
        return pessoaRepository.saveAndFlush(p);
    }

    @DeleteMapping("/{id}")
    public void removerPessoa(@PathVariable Long id) {
        pessoaRepository.deleteById(id);
    }
}
