package br.com.zupacademy.proposta.cartao.carteira;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AssociaCarteiraResponse {


    private final String resultado;
    private final String id;

    @JsonCreator
    public AssociaCarteiraResponse(@JsonProperty String resultado, @JsonProperty String id) {
        this.resultado = resultado;
        this.id = id;
    }

    public String getResultado() {
        return resultado;
    }

    public String getIdAssociacao() {
        return id;
    }
}
