package br.com.zupacademy.proposta.proposta;

import br.com.zupacademy.proposta.error.FieldErrors;
import br.com.zupacademy.proposta.restricaoFinanceira.ConsultaRestricaoSolicitanteFeignClient;
import br.com.zupacademy.proposta.restricaoFinanceira.ConsultaRestricaoSolicitanteRequest;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

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
    public ResponseEntity<?> criaProposta(@Valid @RequestBody PropostaRequest propostaRequest, UriComponentsBuilder builder) {

        if (repository.existsByDocumento(propostaRequest.getDocumento())) {
            logger.warn("Tentativa de criação de uma proposta com o documento {} que já existe no sistema", propostaRequest.getDocumento());
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(new FieldErrors(propostaRequest.jaExistePropostaIgual()));
        }

        Proposta proposta = propostaRequest.toModel();
        repository.save(proposta);
        logger.info("Proposta documento={} salário={} criada com sucesso!", proposta.getDocumento(), proposta.getSalario());

        Boolean solicitanteNaoTemRestricao = verificarRestricao(new ConsultaRestricaoSolicitanteRequest(proposta));
        proposta.validarRestricao(solicitanteNaoTemRestricao);


        repository.save(proposta);
        logger.info("Status da análise financeira atualizado com sucesso! Id da proposta: {} Status:{}", proposta.getId(), proposta.getStatus());

        URI uri = builder.path("/api/propostas/{id}")
                .buildAndExpand(proposta.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    private Boolean verificarRestricao(ConsultaRestricaoSolicitanteRequest request) {
        try {
            consultaRestricaoSolicitanteFeignClient.retornaConsultaFinanceiraSolicitante(request);
            return true;

        } catch (FeignException.UnprocessableEntity e) {
            return false;

        } catch (Exception e) {

            logger.error("Erro ao processar a requisição http para solicitar a consulta financeira!!", e);
            Assert.state(false, "Erro ao processar a requisição http para solicitar a consulta financeira!!");
            return false;
        }
    }
}
