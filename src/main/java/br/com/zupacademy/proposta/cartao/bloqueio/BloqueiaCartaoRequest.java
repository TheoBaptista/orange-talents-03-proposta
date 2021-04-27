package br.com.zupacademy.proposta.cartao.bloqueio;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

public class BloqueiaCartaoRequest {

    @JsonProperty @NotBlank
    private final String sistemaResponsavel;

    public BloqueiaCartaoRequest(String sistemaResponsavel) {
        this.sistemaResponsavel = sistemaResponsavel;
    }
}
