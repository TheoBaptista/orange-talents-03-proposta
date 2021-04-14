package br.com.zupacademy.proposta.proposta;

import br.com.zupacademy.proposta.error.FieldErrors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("api/propostas")
public class CriaPropostaController {

    private final PropostaRepository repository;
    private final Logger logger = LoggerFactory.getLogger(CriaPropostaController.class);

    public CriaPropostaController(PropostaRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> criaProposta(@Valid @RequestBody PropostaRequest propostaRequest, UriComponentsBuilder builder){

        if(repository.existsByDocumento(propostaRequest.getDocumento())){
           return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(new FieldErrors(propostaRequest.jaExistePropostaIgual()));
        }

        Proposta proposta = propostaRequest.toModel();
        repository.save(proposta);

        String message = String.format("Proposta %s criada com sucesso!",proposta.getId());
        logger.info(message);

        URI uri = builder.path("/api/propostas/{id}")
                .buildAndExpand(proposta.getId()).toUri();

        return ResponseEntity.ok().body(uri);
    }

}
