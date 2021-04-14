package br.com.zupacademy.proposta.proposta;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Locale;
import java.util.UUID;

@Entity
public class Proposta {

    @Id
    public String id;
    @Column(nullable = false,unique = true)
    public String documento;
    @Column(nullable = false)
    public String email;
    @Column(nullable = false)
    public String nome;
    @Column(nullable = false)
    public String endereco;
    @Column(nullable = false)
    public BigDecimal salario;

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
}
