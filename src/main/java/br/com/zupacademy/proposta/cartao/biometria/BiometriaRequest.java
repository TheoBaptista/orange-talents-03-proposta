package br.com.zupacademy.proposta.cartao.biometria;

import br.com.zupacademy.proposta.cartao.Cartao;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import java.util.Base64;

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

    public Boolean validarBiometriaBase64() {
        try {
            Base64.getDecoder().decode(this.biometria);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Biometria toModel(Cartao cartao) {
        return new Biometria(this.biometria, cartao);
    }
}
