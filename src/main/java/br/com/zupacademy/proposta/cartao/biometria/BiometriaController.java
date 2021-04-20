package br.com.zupacademy.proposta.cartao.biometria;

import br.com.zupacademy.proposta.cartao.Cartao;
import br.com.zupacademy.proposta.cartao.CartaoRepository;
import br.com.zupacademy.proposta.error.FieldErrors;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("api/cartoes")
public class BiometriaController {

    private final CartaoRepository cartaoRepository;
    private final Logger logger = LoggerFactory.getLogger(BiometriaController.class);
    private final BiometriaRepository biometriaRepository;

    public BiometriaController(CartaoRepository cartaoRepository, BiometriaRepository biometriaRepository) {
        this.cartaoRepository = cartaoRepository;
        this.biometriaRepository = biometriaRepository;
    }

    @PostMapping("/{id}/biometria")
    public ResponseEntity<?> criaBiometria(@PathVariable("id") String cartaoId, @RequestBody @Valid BiometriaRequest request,
                                           UriComponentsBuilder builder){

        Optional<Cartao> optionalCartao = cartaoRepository.findById(cartaoId);

        if (optionalCartao.isEmpty()) {
            logger.warn("Cartão com o id {} não encontrado",cartaoId);
            return ResponseEntity.notFound().build();
        }

        if(Base64.isBase64(request.getBiometria())) {

            Cartao cartao = optionalCartao.get();
            Biometria biometria = request.toModel(cartao);
            biometriaRepository.save(biometria);

            logger.info("Biometria {} cadastada. Id do cartão {}, Hora do registro: {}",biometria.getId(),cartao.getId(),biometria.getDateTime());

            return ResponseEntity.created(builder.path("biometria/{id}").build(biometria.getId())).build();
        }

        logger.warn("Tentiva de cadastro de uma biometria em formato inválido! Id do cartão {}",cartaoId);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new FieldErrors("Campo biometria : Está em formato inválido"));
    }

}
