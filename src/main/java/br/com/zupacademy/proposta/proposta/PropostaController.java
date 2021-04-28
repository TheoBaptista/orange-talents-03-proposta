package br.com.zupacademy.proposta.proposta;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("api/propostas")
public class PropostaController {

    private final PropostaRepository repository;
    private final Timer consultaPropostaTimer;

    public PropostaController(PropostaRepository repository, MeterRegistry meterRegistry) {
        this.repository = repository;
        this.consultaPropostaTimer = meterRegistry.timer("consultar_proposta");
    }

    @GetMapping("/{id}")
    public ResponseEntity<PropostaResponse> listaPropostaPorId(@PathVariable("id") String propostaId) {
        var start = System.currentTimeMillis();

        var proposta = repository.findById(propostaId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        consultaPropostaTimer.record(System.currentTimeMillis() - start, TimeUnit.MILLISECONDS);
        return ResponseEntity.ok().body(new PropostaResponse(proposta));
    }

}
