package br.com.zupacademy.proposta.cartao.carteira;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class AssociarCarteiraRequest {

    @Email
    @NotBlank
    private final String email;

    @NotBlank
    private final String carteira;

    public AssociarCarteiraRequest(String email, String carteira) {
        this.email = email;
        this.carteira = carteira;
    }

    public String getEmail() {
        return email;
    }

    public String getCarteira() {
        return carteira;
    }
}
