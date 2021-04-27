package br.com.zupacademy.proposta.cartao.consulta;

import br.com.zupacademy.proposta.proposta.Proposta;

import javax.validation.constraints.NotBlank;

public class ConsultaCartaoRequest {

    @NotBlank
    private final String documento;
    @NotBlank
    private final String nome;
    @NotBlank
    private final String idProposta;

    public ConsultaCartaoRequest(Proposta proposta) {
        this.documento = proposta.getDocumento();
        this.nome = proposta.getNome();
        this.idProposta = proposta.getId();
    }

    public String getDocumento() {
        return documento;
    }

    public String getNome() {
        return nome;
    }

    public String getIdProposta() {
        return idProposta;
    }
}
