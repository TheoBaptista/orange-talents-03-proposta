package br.com.zupacademy.proposta.proposta;

import br.com.zupacademy.proposta.error.FieldErrors;
import br.com.zupacademy.proposta.feign.ConsultaRestricaoFinanceiraSolicitanteFeignClient;
import feign.FeignException;
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

@RestController
@RequestMapping("api/propostas")
public class CriaPropostaController {

    private final PropostaRepository repository;
    private final Logger logger = LoggerFactory.getLogger(CriaPropostaController.class);
    private final ConsultaRestricaoFinanceiraSolicitanteFeignClient consultaRestricaoFinanceiraSolicitanteFeignClient;

    public CriaPropostaController(PropostaRepository repository, ConsultaRestricaoFinanceiraSolicitanteFeignClient consultaRestricaoFinanceiraSolicitanteFeignClient) {
        this.repository = repository;
        this.consultaRestricaoFinanceiraSolicitanteFeignClient = consultaRestricaoFinanceiraSolicitanteFeignClient;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> criaProposta(@Valid @RequestBody NovaPropostaRequest novaPropostaRequest, UriComponentsBuilder builder) {

        if (repository.existsByDocumento(novaPropostaRequest.getDocumento())) {
            logger.warn("Tentativa de criação de uma proposta com o documento {} que já existe no sistema", novaPropostaRequest.getDocumento());
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(new FieldErrors(novaPropostaRequest.jaExistePropostaIgual()));
        }

        Proposta proposta = novaPropostaRequest.toModel();

        Boolean solicitanteNaoTemRestricao = verificarRestricao(new ConsultaPropostaRequest(proposta));
        proposta.validarRestricao(solicitanteNaoTemRestricao);

        repository.save(proposta);
        logger.info("Proposta criada com sucesso! documento={} salário={} status da proposta={}", proposta.getDocumento(), proposta.getSalario(), proposta.getStatus());

        return ResponseEntity.created(builder.path("/api/propostas/{id}").build(proposta.getId())).build();
    }

    private Boolean verificarRestricao(ConsultaPropostaRequest request) {
        try {
            consultaRestricaoFinanceiraSolicitanteFeignClient.consultaRestricaoFinanceiraSolicitante(request);
            return true;

        } catch (FeignException.UnprocessableEntity e) {
            return false;
        }
    }
}
