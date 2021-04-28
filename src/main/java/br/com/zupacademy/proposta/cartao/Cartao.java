package br.com.zupacademy.proposta.cartao;

import br.com.zupacademy.proposta.cartao.biometria.Biometria;
import br.com.zupacademy.proposta.cartao.bloqueio.BloqueiaCartaoRequest;
import br.com.zupacademy.proposta.cartao.carteira.Carteira;
import br.com.zupacademy.proposta.cartao.carteira.TipoCarteira;
import br.com.zupacademy.proposta.feign.CartaoFeignClient;
import br.com.zupacademy.proposta.proposta.Proposta;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
public class Cartao {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "cartao_id")
    private String id;
    @Column(nullable = false)
    private String numero;
    @Column(nullable = false)
    private String titular;
    @OneToOne(mappedBy = "cartao")
    private Proposta proposta;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cartao")
    private List<Biometria> biometrias;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusCartao statusCartao;
    @OneToMany(mappedBy = "cartao")
    private List<Carteira> carteiras;

    /**
     * @deprecated (Hibernate only)
     */
    @Deprecated(forRemoval = false)
    public Cartao() {
    }

    public Cartao(String numero, String titular) {
        this.numero = numero;
        this.titular = titular;
        this.statusCartao = StatusCartao.DESBLOQUEADO;
    }

    public String getId() {
        return id;
    }

    public String getTitular() {
        return titular;
    }

    public String getNumero() {
        return numero;
    }

    public String numeroCartaoOfuscado() {
        return this.numero.substring(15);
    }

    public void adicionarBiometria(Biometria biometria) {
        this.biometrias.add(biometria);
    }

    public StatusCartao getStatusCartao() {
        return statusCartao;
    }

    public void bloquearCartao(CartaoFeignClient cartaoFeignClient) {
        cartaoFeignClient.bloqueiaCartao(new BloqueiaCartaoRequest("Proposta"), this.getNumero());
        this.statusCartao = StatusCartao.BLOQUEADO;
    }

    public boolean validarCarteiraIgual(TipoCarteira novaCarteira) {
        return this.carteiras.stream().anyMatch(carteira -> carteira.getTipoCarteira().equals(novaCarteira));
    }
}
