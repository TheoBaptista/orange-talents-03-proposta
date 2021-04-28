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
    private String biometriaBase64;
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

    public Biometria(String biometriaBase64, Cartao cartao) {
        Assert.isTrue(Base64.isBase64(biometriaBase64), "Biometria está em formato inválido");
        this.biometriaBase64 = biometriaBase64;
        this.cartao = cartao;
    }

    public String getId() {
        return id;
    }

    public String getBiometriaBase64() {
        return biometriaBase64;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

}
