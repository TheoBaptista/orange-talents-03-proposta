package br.com.zupacademy.proposta.proposta;

import br.com.zupacademy.proposta.cartao.Cartao;
import br.com.zupacademy.proposta.feign.ConsultaRestricaoFinanceiraSolicitanteFeignClient;
import feign.FeignException;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Locale;
import java.util.UUID;

@Entity
public class Proposta {

    @Id
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
    private AnaliseFinanceiraStatus status;
    @Valid
    @OneToOne
    private Cartao cartao;

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
        this.id = UUID.randomUUID().toString();
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

    public String getDocumento() {
        return documento;
    }

    public AnaliseFinanceiraStatus getStatus() {
        return status;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public String documentoOfuscado(){ return this.documento.substring(1,6);}

    public void verificarRestricaoFinanceira(ConsultaRestricaoFinanceiraSolicitanteFeignClient consultaRestricaoFinanceiraSolicitanteFeignClient) {
        try {
            consultaRestricaoFinanceiraSolicitanteFeignClient.consultaRestricaoFinanceiraSolicitante(new ConsultaPropostaRequest(this));
            this.status = AnaliseFinanceiraStatus.ELEGIVEL;
        } catch (FeignException.UnprocessableEntity e) {
            this.status = AnaliseFinanceiraStatus.NAO_ELEGIVEL;
        }
        Assert.state(this.status!=null,"O status não devia ser nulo, há algo errado!");
    }

    public void adicionaCartaoAProposta(Cartao cartao){
        Assert.state(this.cartao==null,"Essa proposta já tem um cartão relacionado");
        this.cartao = cartao;
    }
}
