package br.com.zupacademy.proposta.proposta;

import br.com.zupacademy.proposta.cartao.Cartao;
import br.com.zupacademy.proposta.feign.ConsultaRestricaoFinanceiraSolicitanteFeignClient;
import feign.FeignException;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Locale;

@Entity
public class Proposta {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    @Column(nullable = false, unique = true)
    private String documento;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private String endereco;
    @Column(nullable = false)
    private BigDecimal salario;
    @Enumerated(EnumType.STRING)
    private AnaliseFinanceiraStatus analiseFinanceiraStatus;
    @Valid
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cartao_id")
    private Cartao cartao;
    @Enumerated(EnumType.STRING)
    private StatusProcessamentoCartao statusProcessamentoCartao;

    /**
     * @deprecated (Hibernate only)
     */
    @Deprecated(forRemoval = false)
    public Proposta() {
    }

    public Proposta(String documento, String email, String nome, String endereco, BigDecimal salario) {
        this.documento = documento;
        this.email = email.toLowerCase(Locale.ROOT);
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() { return endereco; }

    public Cartao getCartao() { return cartao; }

    public String getDocumento() {
        return documento;
    }

    public AnaliseFinanceiraStatus getAnaliseFinanceiraStatus() {
        return analiseFinanceiraStatus;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public String documentoOfuscado(){ return this.documento.substring(1,6);}

    public StatusProcessamentoCartao getStatusCartao() {
        return statusProcessamentoCartao;
    }

    public void adicionaCartaoAProposta(Cartao cartao){
        Assert.state(this.cartao==null,"Essa proposta já tem um cartão relacionado");
        this.cartao = cartao;
        this.statusProcessamentoCartao = StatusProcessamentoCartao.CRIADO;
    }

    public void verificarRestricaoFinanceira(ConsultaRestricaoFinanceiraSolicitanteFeignClient consultaRestricaoFinanceiraSolicitanteFeignClient) {
        try {
            consultaRestricaoFinanceiraSolicitanteFeignClient.consultaRestricaoFinanceiraSolicitante(new ConsultaPropostaRequest(this));
            this.analiseFinanceiraStatus = AnaliseFinanceiraStatus.ELEGIVEL;
            this.statusProcessamentoCartao = StatusProcessamentoCartao.EM_PROCESSAMENTO;
        } catch (FeignException.UnprocessableEntity e) {
            this.analiseFinanceiraStatus = AnaliseFinanceiraStatus.NAO_ELEGIVEL;
            this.statusProcessamentoCartao = StatusProcessamentoCartao.NEGADO;
        }
        Assert.state(this.analiseFinanceiraStatus !=null,"O status não devia ser nulo, há algo errado!");
    }


}
