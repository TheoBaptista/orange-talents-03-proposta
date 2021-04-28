package br.com.zupacademy.proposta.cartao.viagem;

import br.com.zupacademy.proposta.cartao.Cartao;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.*;
import java.time.LocalDate;

public class AvisoViagemRequest {

    @NotBlank
    private final String destino;
    @NotNull
    @FutureOrPresent
    @JsonFormat(pattern = "yyyy/MM/dd", shape = JsonFormat.Shape.STRING)
    private final LocalDate dataTerminoViagem;

    @JsonCreator
    public AvisoViagemRequest(@JsonProperty String destino, @JsonProperty LocalDate dataTerminoViagem) {
        this.destino = destino;
        this.dataTerminoViagem = dataTerminoViagem;
    }

    public AvisoViagem toModel(String ipResponsavel, String userAgent, Cartao cartao) {
        return new AvisoViagem(ipResponsavel, userAgent, this.destino, this.dataTerminoViagem, cartao);
    }

}
