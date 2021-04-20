package br.com.zupacademy.proposta.cartao.biometria;

import br.com.zupacademy.proposta.cartao.Cartao;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

public class BiometriaRequest {

    @NotBlank
    private final String biometria;

    @JsonCreator
    public BiometriaRequest(@JsonProperty("biometria") String biometria) {
        this.biometria = biometria;
    }

    public String getBiometria() {
        return this.biometria;
    }

    public Biometria toModel(Cartao cartao){
        return new Biometria(this.biometria,cartao);
    }
}
