package br.com.zupacademy.proposta.proposta;

import br.com.zupacademy.proposta.error.FieldErrors;
import br.com.zupacademy.proposta.restricaoFinanceira.ConsultaRestricaoSolicitanteFeignClient;
import br.com.zupacademy.proposta.restricaoFinanceira.ConsultaRestricaoSolicitanteRequest;
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
    private final ConsultaRestricaoSolicitanteFeignClient consultaRestricaoSolicitanteFeignClient;

    public CriaPropostaController(PropostaRepository repository, ConsultaRestricaoSolicitanteFeignClient consultaRestricaoSolicitanteFeignClient) {
        this.repository = repository;
        this.consultaRestricaoSolicitanteFeignClient = consultaRestricaoSolicitanteFeignClient;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> criaProposta(@Valid @RequestBody PropostaRequest propostaRequest, UriComponentsBuilder builder) {

        if (repository.existsByDocumento(propostaRequest.getDocumento())) {
            logger.warn("Tentativa de criação de uma proposta com o documento {} que já existe no sistema", propostaRequest.getDocumento());
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(new FieldErrors(propostaRequest.jaExistePropostaIgual()));
        }

        Proposta proposta = propostaRequest.toModel();



        Boolean solicitanteNaoTemRestricao = verificarRestricao(new ConsultaRestricaoSolicitanteRequest(proposta));
        proposta.validarRestricao(solicitanteNaoTemRestricao);


        repository.save(proposta);
        logger.info("Proposta criada com sucesso! documento={} salário={} status da proposta={}", proposta.getDocumento(), proposta.getSalario(),proposta.getStatus());



        return ResponseEntity.created(builder.path("/api/propostas/{id}").build(proposta.getId())).build();
    }

    private Boolean verificarRestricao(ConsultaRestricaoSolicitanteRequest request) {
        try {
            consultaRestricaoSolicitanteFeignClient.retornaConsultaFinanceiraSolicitante(request);
            return true;

        } catch (FeignException.UnprocessableEntity e) {
            return false;
        }
    }
}
