package br.com.zupacademy.proposta.cartao.consulta;

import br.com.zupacademy.proposta.proposta.Proposta;

public class ConsultaCartaoRequest {

    private final String documento;
    private final String nome;
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
