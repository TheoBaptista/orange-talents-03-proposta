package br.com.zupacademy.proposta.cartao;

import br.com.zupacademy.proposta.cartao.bloqueio.BloqueioCartao;
import br.com.zupacademy.proposta.cartao.bloqueio.BloqueioCartaoRepository;
import br.com.zupacademy.proposta.error.FieldErrors;
import br.com.zupacademy.proposta.feign.CartaoFeignClient;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

@RestController
@RequestMapping("/api/cartoes")
public class CartaoController {

    private final CartaoRepository cartaoRepository;
    private final BloqueioCartaoRepository bloqueioCartaoRepository;
    private final CartaoFeignClient cartaoFeignClient;

    public CartaoController(CartaoRepository cartaoRepository, BloqueioCartaoRepository bloqueioCartaoRepository, CartaoFeignClient cartaoFeignClient) {
        this.cartaoRepository = cartaoRepository;
        this.bloqueioCartaoRepository = bloqueioCartaoRepository;
        this.cartaoFeignClient = cartaoFeignClient;
    }

    @PostMapping("/{id}/bloqueios")
    @Transactional
    public ResponseEntity<?> bloqueiaCartao(@PathVariable("id") String idCartao, HttpServletRequest request) {
        var cartao = cartaoRepository.findById(idCartao).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        try {
            cartao.bloquearCartao(cartaoFeignClient);
            bloqueioCartaoRepository.save(new BloqueioCartao(cartao.getId(), request.getRemoteAddr(), request.getHeader("User-Agent")));
            return ResponseEntity.ok().build();
        } catch (FeignException.UnprocessableEntity e) {
            return ResponseEntity.unprocessableEntity().body(new FieldErrors("Cartão : Já está bloqueado!"));
        } catch (FeignException.BadRequest e) {
            return ResponseEntity.badRequest().body(new FieldErrors("Erro ao processar a solicitação"));
        }
    }

}
