package br.com.zupacademy.proposta.cartao.biometria;

import br.com.zupacademy.proposta.cartao.Cartao;
import org.apache.tomcat.util.codec.binary.Base64;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Biometria {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "biometria_id")
    private String id;
    private String biometria;
    @ManyToOne
    @JoinColumn(name = "cartao_id")
    private Cartao cartao;
    @CreationTimestamp
    private LocalDateTime dateTime;

    /**
     * @deprecated (Hibernate only)
     */
    @Deprecated(forRemoval = false)
    public Biometria() {
    }

    public Biometria(String biometria,Cartao cartao) {
        Assert.isTrue(Base64.isBase64(biometria),"Biometria está em formato inválido");
        this.biometria = biometria;
        this.cartao = cartao;
    }

    public String getId() {
        return id;
    }

    public String getBiometria() {
        return biometria;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

}
