package br.com.zupacademy.proposta.cartao;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ConsultaCartaoResponse {

    private final String id;
    private final String titular;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public ConsultaCartaoResponse(@JsonProperty(value = "id") String id,@JsonProperty(value = "titular") String titular) {
        this.id = id;
        this.titular = titular;
    }

    public Cartao toModel(){
        return new Cartao(this.id,this.titular);
    }
}
