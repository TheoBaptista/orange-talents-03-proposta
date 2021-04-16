package br.com.zupacademy.proposta.proposta;

import br.com.zupacademy.proposta.compartilhado.CPForCNPJConstraint;

import javax.validation.constraints.*;
import java.math.BigDecimal;

public class NovaPropostaRequest {

    @NotBlank
    @CPForCNPJConstraint
    @Pattern(regexp = "\\d+",message ="O documento deve conter apenas números!")
    private final String documento;
    @NotBlank
    @Email
    private final String email;
    @NotBlank
    private final String nome;
    @NotBlank
    private final String endereco;
    @NotNull
    @PositiveOrZero
    private final BigDecimal salario;

    public NovaPropostaRequest(@NotBlank String documento, @NotBlank @Email String email, @NotBlank String nome, @NotBlank String endereco, @NotNull @PositiveOrZero BigDecimal salario) {
        this.documento = documento;
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
    }

    public String getDocumento() {
        return documento;
    }

    public String jaExistePropostaIgual() {
        return String.format("Campo documento : Já existe um documento com esse número!");
    }

    public String documentoOfuscado(){
        return this.documento.substring(6);
    }

    public Proposta toModel() {
        return new Proposta(this.documento, this.email, this.nome, this.endereco, this.salario);
    }

}
