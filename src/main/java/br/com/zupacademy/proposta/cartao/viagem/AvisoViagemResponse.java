package br.com.zupacademy.proposta.cartao.viagem;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AvisoViagemResponse {

    public String resultado;

    @JsonCreator
    public AvisoViagemResponse(@JsonProperty String resultado) {
        this.resultado = resultado;
    }
}
