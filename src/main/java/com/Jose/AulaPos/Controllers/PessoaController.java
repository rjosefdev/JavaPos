package com.Jose.AulaPos.Controllers;

import com.Jose.AulaPos.Models.Pessoa;
import com.Jose.AulaPos.Repositories.PessoaRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pessoas")
@Tag(
        name = "[Fullstack Developer] Aula 1 - Springboot API REST",
        description = "CRUD simples de pessoas utlizando JPA e H2."
)
public class PessoaController {
    @Autowired
    private PessoaRepository pessoaRepository;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Cadastrar pessoa",
            description = "Cria uma nova pessoa no banco de dados usando o nome informado.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados da pessoa que será cadastrada.",
                    required = true,
                    content = @Content(schema = @Schema(implementation = Pessoa.class))
            )
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Pessoa cadastrada com sucesso.",
                    content = @Content(schema = @Schema(implementation = Pessoa.class))
            ),
            @ApiResponse(responseCode = "400", description = "Dados inválidos.", content = @Content)
    })
    public Pessoa adicionar(@RequestBody Pessoa p) {
        return pessoaRepository.saveAndFlush(p);
    }

    @GetMapping()
    @Operation(
            summary = "Listar pessoas",
            description = "Retorna todas as pessoas cadastradas no banco de dados."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de pessoas retornada com sucesso.",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Pessoa.class)))
            )
    })
    public List<Pessoa> listar() {
        return pessoaRepository.findAll();
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar pessoa por ID",
            description = "Retorna uma pessoa cadastrada a partir do seu identificador."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Pessoa encontrada.",
                    content = @Content(schema = @Schema(implementation = Pessoa.class))
            )
    })
    public Optional<Pessoa> listarById(
            @Parameter(description = "Identificador da pessoa.", example = "1")
            @PathVariable Long id
    ) {
        try{
            return pessoaRepository.findById(id);
        } catch (Exception ex){
            return Optional.empty();
        }
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar pessoa",
            description = "Atualiza todos os dados editáveis de uma pessoa existente.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Novos dados da pessoa.",
                    required = true,
                    content = @Content(schema = @Schema(implementation = Pessoa.class))
            )
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Pessoa atualizada com sucesso.",
                    content = @Content(schema = @Schema(implementation = Pessoa.class))
            ),
            @ApiResponse(responseCode = "404", description = "Pessoa não encontrada.", content = @Content)
    })
    public Pessoa alterar(
            @Parameter(description = "Identificador da pessoa.", example = "1")
            @PathVariable Long id,
            @RequestBody Pessoa novaPessoa
    ) {
        Pessoa p = pessoaRepository.findById(id)
                .orElseThrow(()-> new
                        ResponseStatusException(HttpStatus.NOT_FOUND, "Pessoa não encontrada!"));
        p.setNome(novaPessoa.getNome());
        return pessoaRepository.saveAndFlush(p);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Remover pessoa",
            description = "Remove uma pessoa cadastrada a partir do seu identificador."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pessoa removida com sucesso."),
            @ApiResponse(responseCode = "404", description = "Pessoa não encontrada.", content = @Content)
    })
    public void removerPessoa(
            @Parameter(description = "Identificador da pessoa.", example = "1")
            @PathVariable Long id
    ) {
        pessoaRepository.deleteById(id);
    }
}
