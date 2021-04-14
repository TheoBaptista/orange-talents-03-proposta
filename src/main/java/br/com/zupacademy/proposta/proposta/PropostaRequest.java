package br.com.zupacademy.proposta.proposta;

import br.com.zupacademy.proposta.shared.CPForCNPJConstraint;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;

public class PropostaRequest {

    @NotBlank @CPForCNPJConstraint @Pattern(regexp = "\\d+",message = "Deve conter apenas núemeros!")
    private String documento;
    @NotBlank @Email
    private String email;
    @NotBlank
    private String nome;
    @NotBlank
    private String endereco;
    @NotNull @PositiveOrZero
    private BigDecimal salario;

    public PropostaRequest(@NotBlank String documento, @NotBlank @Email String email, @NotBlank String nome, @NotBlank String endereco, @NotNull @PositiveOrZero BigDecimal salario) {
        this.documento = documento;
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
    }

    public Proposta toModel(){
        return new Proposta(this.documento,this.email,this.nome,this.endereco,this.salario);
    }

}
