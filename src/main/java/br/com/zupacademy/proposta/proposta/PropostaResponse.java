package br.com.zupacademy.proposta.proposta;

import java.math.BigDecimal;

public class PropostaResponse {

    private final String nome;
    private final String email;
    private final String documento;
    private final String endereco;
    private final BigDecimal salario;
    private final AnaliseFinanceiraStatus status;
    private final StatusProcessamentoCartao statusProcessamentoCartao;


    public PropostaResponse(Proposta proposta) {
        this.nome = proposta.getNome();
        this.documento = proposta.getDocumento();
        this.status = proposta.getAnaliseFinanceiraStatus();
        this.statusProcessamentoCartao = proposta.getStatusCartao();
        this.email = proposta.getEmail();
        this.endereco = proposta.getEndereco();
        this.salario = proposta.getSalario();
    }

    public String getEmail() {
        return email;
    }

    public String getEndereco() {
        return endereco;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public StatusProcessamentoCartao getStatusCartao() {
        return statusProcessamentoCartao;
    }

    public String getNome() {
        return nome;
    }

    public AnaliseFinanceiraStatus getStatus() {
        return status;
    }

    public String getDocumento() {
        return documento;
    }
}
