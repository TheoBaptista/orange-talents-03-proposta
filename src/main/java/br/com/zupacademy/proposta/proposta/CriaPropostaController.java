package br.com.zupacademy.proposta.proposta;

import br.com.zupacademy.proposta.error.FieldErrors;
import br.com.zupacademy.proposta.feign.ConsultaRestricaoFinanceiraSolicitanteFeignClient;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
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
import java.util.List;

@RestController
@RequestMapping("api/propostas")
public class CriaPropostaController {

    private final PropostaRepository repository;
    private final Logger logger = LoggerFactory.getLogger(CriaPropostaController.class);
    private final ConsultaRestricaoFinanceiraSolicitanteFeignClient consultaRestricaoFinanceiraSolicitanteFeignClient;
    private final Counter contadorDePropostasCriadas;


    public CriaPropostaController(PropostaRepository repository, ConsultaRestricaoFinanceiraSolicitanteFeignClient consultaRestricaoFinanceiraSolicitanteFeignClient, MeterRegistry meterRegistry) {
        this.repository = repository;
        this.consultaRestricaoFinanceiraSolicitanteFeignClient = consultaRestricaoFinanceiraSolicitanteFeignClient;
        this.contadorDePropostasCriadas = meterRegistry.counter("proposta_criada");
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> criaProposta(@Valid @RequestBody NovaPropostaRequest novaPropostaRequest, UriComponentsBuilder builder) {

        List<Proposta> propostas = repository.findAll();
        boolean anyMatch = propostas.stream().anyMatch(proposta -> proposta.getDocumento().equals(novaPropostaRequest.getDocumento()));


        if (anyMatch) {
            logger.warn("Tentativa de criação de uma proposta com o documento que já existe no sistema");
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(new FieldErrors("Campo documento : Já existe uma proposta com esse número de documento!"));
        }

        var proposta = novaPropostaRequest.toModel();

        proposta.verificarRestricaoFinanceira(consultaRestricaoFinanceiraSolicitanteFeignClient);

        repository.save(proposta);
        logger.info("Proposta criada com sucesso! id da proposta={} salário={} status da proposta={}",proposta.getId(), proposta.getSalario(), proposta.getAnaliseFinanceiraStatus());
        contadorDePropostasCriadas.increment();

        return ResponseEntity.created(builder.path("/api/propostas/{id}").build(proposta.getId())).build();
    }
}
