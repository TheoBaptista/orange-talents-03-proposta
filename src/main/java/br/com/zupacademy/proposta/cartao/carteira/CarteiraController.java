package br.com.zupacademy.proposta.cartao.carteira;

import br.com.zupacademy.proposta.cartao.Cartao;
import br.com.zupacademy.proposta.cartao.CartaoRepository;
import br.com.zupacademy.proposta.feign.CartaoFeignClient;
import feign.FeignException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("api/cartoes")
public class CarteiraController {

    private final CarteiraRepository carteiraRepository;
    private final CartaoRepository cartaoRepository;
    private final CartaoFeignClient cartaoFeignClient;

    public CarteiraController(CarteiraRepository carteiraRepository, CartaoRepository cartaoRepository, CartaoFeignClient cartaoFeignClient) {
        this.carteiraRepository = carteiraRepository;
        this.cartaoRepository = cartaoRepository;
        this.cartaoFeignClient = cartaoFeignClient;
    }

    @PostMapping("/{id}/carteiras")
    @Transactional
    public ResponseEntity<?> associarCartaoComCarteira(@PathVariable("id") String cartaoId, @RequestBody @Valid CarteiraRequest carteiraRequest, UriComponentsBuilder builder) {

        Optional<Cartao> optionalCartao = cartaoRepository.findById(cartaoId);
        if (optionalCartao.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var cartao = optionalCartao.get();
        if (cartao.validarCarteiraIgual(carteiraRequest.getTipoCarteira())) {
            return ResponseEntity.unprocessableEntity().build();
        }


        try {
            var associaCarteiraResponse = cartaoFeignClient.associarCarteira(carteiraRequest.buildRequest(), cartao.getNumero());
            var carteira = carteiraRequest.toModel(cartao, associaCarteiraResponse.getIdAssociacao());
            carteiraRepository.save(carteira);
            return ResponseEntity.created(builder.path("/api/cartoes/carteira/{id}").build(carteira.getId())).build();
        } catch (FeignException e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
