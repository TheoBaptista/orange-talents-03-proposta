package br.com.zupacademy.proposta.proposta;

public class ConsultaPropostaResponse {

    private final String documento;
    private final String nome;
    private final String resultadoSolicitacao;
    private final String idProposta;

    public ConsultaPropostaResponse(String documento, String nome, String resultadoSolicitacao, String idProposta) {
        this.documento = documento;
        this.nome = nome;
        this.resultadoSolicitacao = resultadoSolicitacao;
        this.idProposta = idProposta;
    }

    public String getDocumento() {
        return documento;
    }

    public String getNome() {
        return nome;
    }

    public String getResultadoSolicitacao() {
        return resultadoSolicitacao;
    }

    public String getIdProposta() {
        return idProposta;
    }
}
