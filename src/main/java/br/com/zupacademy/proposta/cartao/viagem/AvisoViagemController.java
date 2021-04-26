package br.com.zupacademy.proposta.cartao.viagem;

import br.com.zupacademy.proposta.cartao.Cartao;
import br.com.zupacademy.proposta.cartao.CartaoRepository;
import br.com.zupacademy.proposta.error.FieldErrors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("api/cartoes")
public class AvisoViagemController {

    private final CartaoRepository cartaoRepository;
    private final AvisoViagemRepository avisoViagemRepository;

    public AvisoViagemController(CartaoRepository cartaoRepository, AvisoViagemRepository avisoViagemRepository) {
        this.cartaoRepository = cartaoRepository;
        this.avisoViagemRepository = avisoViagemRepository;
    }

    @PostMapping("/aviso-viagem/{id}")
    @Transactional
    public ResponseEntity<?> criaAvisoViagem(@PathVariable("id") String idCartao, HttpServletRequest request,@Valid @RequestBody AvisoViagemRequest avisoViagemRequest){

        Optional<Cartao> optionalCartao = cartaoRepository.findById(idCartao);

        if(optionalCartao.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new FieldErrors("Cartão : Não há cartão com esse id!"));

        AvisoViagem avisoViagem = avisoViagemRequest.toModel(request.getRemoteAddr(), request.getHeader("User-agent"),optionalCartao.get());

        avisoViagemRepository.save(avisoViagem);

        return ResponseEntity.ok().build();
    }

}
