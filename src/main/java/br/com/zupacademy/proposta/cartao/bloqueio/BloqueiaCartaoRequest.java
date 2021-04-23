package br.com.zupacademy.proposta.cartao.bloqueio;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BloqueiaCartaoRequest {

    @JsonProperty
    private final String sistemaResponsavel;

    public BloqueiaCartaoRequest(String sistemaResponsavel) {
        this.sistemaResponsavel = sistemaResponsavel;
    }
}
