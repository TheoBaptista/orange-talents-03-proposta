package br.com.zupacademy.proposta.cartao.bloqueio;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BloqueiaCartaoResponse {


     private final String resultado;

    @JsonCreator
    public BloqueiaCartaoResponse(@JsonProperty("resultado") String resultado) {
        this.resultado = resultado;
    }

    public String getResultado() {
        return resultado;
    }
}
