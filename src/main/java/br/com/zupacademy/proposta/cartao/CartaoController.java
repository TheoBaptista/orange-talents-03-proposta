package br.com.zupacademy.proposta.cartao;

import br.com.zupacademy.proposta.cartao.bloqueio.BloqueioCartao;
import br.com.zupacademy.proposta.cartao.bloqueio.BloqueioCartaoRepository;
import br.com.zupacademy.proposta.error.FieldErrors;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@RequestMapping("/api/cartoes")
public class CartaoController {

    private final CartaoRepository cartaoRepository;
    private final BloqueioCartaoRepository bloqueioCartaoRepository;

    public CartaoController(CartaoRepository cartaoRepository, BloqueioCartaoRepository bloqueioCartaoRepository) {
        this.cartaoRepository = cartaoRepository;
        this.bloqueioCartaoRepository = bloqueioCartaoRepository;
    }

    @PostMapping("/bloqueios/{id}")
    public ResponseEntity<?> bloqueiaCartao(@PathVariable("id") String idCartao, HttpServletRequest request){
        Optional<Cartao> cartaoOptional = cartaoRepository.findById(idCartao);

        if(cartaoOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        Cartao cartao = cartaoOptional.get();

        if(cartao.bloquearCartao()){
            bloqueioCartaoRepository.save(new BloqueioCartao(cartao.getId(), request.getRemoteAddr(), request.getHeader("User-Agent")));
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.unprocessableEntity().body(new FieldErrors("Cartão : Já está bloqueado"));
    }

}
