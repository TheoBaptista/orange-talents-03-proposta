package br.com.zupacademy.proposta.cartao;

import br.com.zupacademy.proposta.proposta.Proposta;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.UUID;

@Entity
public class Cartao {

    @Id
    @Column(name = "cartao_id")
    private String id;
    @Column(nullable = false)
    private String numero;
    @Column(nullable = false)
    private String titular;
    @OneToOne(mappedBy = "cartao")
    private Proposta proposta;

    /**
     * @deprecated (Hibernate only)
     */
    public Cartao() {
    }

    public Cartao(String numero, String titular) {
        this.id = UUID.randomUUID().toString();
        this.numero = numero;
        this.titular = titular;
    }

    public String getNumero() {
        return numero;
    }

    public String numeroCartaoOfuscado(){
        return this.numero.substring(15);
    }
}
