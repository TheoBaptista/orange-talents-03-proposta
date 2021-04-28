package br.com.zupacademy.proposta.cartao.bloqueio;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class BloqueioCartao {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    @Column(nullable = false)
    private String idCartao;
    @CreationTimestamp
    private LocalDateTime momentoRegistro;
    @Column(nullable = false)
    private String ipResponsavel;
    @Column(nullable = false)
    private String userAgent;

    public BloqueioCartao(String idCartao, String ipResponsavel, String userAgent) {
        this.idCartao = idCartao;
        this.ipResponsavel = ipResponsavel;
        this.userAgent = userAgent;
    }

    /**
     * @deprecated (Hibernate only)
     */
    @Deprecated(forRemoval = false)
    public BloqueioCartao() {
    }

    public String getId() {
        return id;
    }

}
