package br.com.zupacademy.proposta.cartao.viagem;

import br.com.zupacademy.proposta.cartao.Cartao;
import br.com.zupacademy.proposta.cartao.StatusCartao;
import br.com.zupacademy.proposta.feign.CartaoFeignClient;
import feign.FeignException;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class AvisoViagem {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    @CreationTimestamp
    private LocalDateTime instanteRegistro;
    @Column(nullable = false)
    @NotBlank
    private String ipResponsavel;
    @Column(nullable = false)
    @NotBlank
    private String userAgent;
    @Column(nullable = false)
    private String destino;
    @Column(nullable = false)
    private LocalDate dataTerminoViagem;
    @ManyToOne
    @JoinColumn(nullable = false, name = "cartao_id")
    private Cartao cartao;

    public AvisoViagem(@Valid String ipResponsavel, @Valid String userAgent, String destino, LocalDate dataTerminoViagem, Cartao cartao) {
        this.ipResponsavel = ipResponsavel;
        this.userAgent = userAgent;
        this.destino = destino;
        this.dataTerminoViagem = dataTerminoViagem;
        this.cartao = cartao;
    }

    /**
     * @deprecated (Hibernate only)
     */
    @Deprecated(forRemoval = false)
    public AvisoViagem() {
    }

    public String getId() {
        return id;
    }

    public LocalDateTime getInstanteRegistro() {
        return instanteRegistro;
    }

    public String getIpResponsavel() {
        return ipResponsavel;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public String getDestino() {
        return destino;
    }

    public LocalDate getDataTerminoViagem() {
        return dataTerminoViagem;
    }

    public Boolean notificarViagem(CartaoFeignClient cartaoFeignClient) {
        Assert.state(this.cartao.getStatusCartao().equals(StatusCartao.DESBLOQUEADO), "O cartão está bloqueado!");

        try {
            AvisoViagemResponse avisoViagemResponse = cartaoFeignClient.notificarViagem(new AvisoViagemRequest(this.destino, this.dataTerminoViagem), this.cartao.getNumero());
            return true;
        }catch (FeignException e){
            return false;
        }
    }
}
