package br.com.zupacademy.proposta.cartao.carteira;

import br.com.zupacademy.proposta.cartao.Cartao;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Locale;

public class CarteiraRequest {

    @NotBlank
    @Email
    private final String email;
    @NotNull
    private final TipoCarteira tipoCarteira;

    @JsonCreator
    public CarteiraRequest(@JsonProperty String email, @JsonProperty String tipoCarteira) {
        this.email = email;
        this.tipoCarteira = TipoCarteira.valueOf(tipoCarteira.toUpperCase(Locale.ROOT));
    }

    public TipoCarteira getTipoCarteira() {
        return tipoCarteira;
    }

    public Carteira toModel(Cartao cartao, String idAssociacao) {
        return new Carteira(this.email, idAssociacao, this.tipoCarteira, cartao);
    }

    public AssociarCarteiraRequest buildRequest() {
        return new AssociarCarteiraRequest(this.email, this.tipoCarteira.toString());
    }
}
