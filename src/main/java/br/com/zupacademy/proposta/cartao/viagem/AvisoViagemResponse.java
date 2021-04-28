package br.com.zupacademy.proposta.cartao.viagem;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AvisoViagemResponse {

    @JsonProperty
    private final String resultado;

    @JsonCreator
    public AvisoViagemResponse(@JsonProperty("resultado") String resultado) {
        this.resultado = resultado;
    }

    public String getResultado() {
        return resultado;
    }
}
