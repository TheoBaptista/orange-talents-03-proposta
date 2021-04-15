package br.com.zupacademy.proposta.restricaoFinanceira;

import br.com.zupacademy.proposta.proposta.Proposta;

public class ConsultaRestricaoSolicitanteRequest {

    private String documento;
    private String nome;
    private String idProposta;

    public ConsultaRestricaoSolicitanteRequest(String documento, String nome, String idProposta) {
        this.documento = documento;
        this.nome = nome;
        this.idProposta = idProposta;
    }

    public ConsultaRestricaoSolicitanteRequest(Proposta proposta) {
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
