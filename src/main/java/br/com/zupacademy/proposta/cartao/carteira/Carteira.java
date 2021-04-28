package br.com.zupacademy.proposta.cartao.carteira;

import br.com.zupacademy.proposta.cartao.Cartao;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class Carteira {


    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    @Column(nullable = false)
    private String idAssociacao;
    @Column(nullable = false)
    private String email;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoCarteira tipoCarteira;
    @JoinColumn(nullable = false)
    @ManyToOne
    private Cartao cartao;

    public Carteira(String email, String idAssociacao, TipoCarteira tipoCarteira, Cartao cartao) {
        this.email = email.toLowerCase();
        this.idAssociacao = idAssociacao;
        this.tipoCarteira = tipoCarteira;
        this.cartao = cartao;
    }

    /**
     * @deprecated (Hibernate only)
     */
    @Deprecated(forRemoval = false)
    public Carteira() {
    }

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }

    public TipoCarteira getTipoCarteira() {
        return tipoCarteira;
    }
}
