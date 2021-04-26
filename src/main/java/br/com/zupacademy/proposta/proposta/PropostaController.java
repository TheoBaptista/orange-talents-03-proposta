package br.com.zupacademy.proposta.proposta;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
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
    public ResponseEntity<PropostaResponse> listaPropostaPorId(@PathVariable("id") String propostaId){
        Long start = System.currentTimeMillis();

        Optional<Proposta> optional = repository.findById(propostaId);

        if(optional.isEmpty()) return ResponseEntity.notFound().build();

        consultaPropostaTimer.record(System.currentTimeMillis() - start ,TimeUnit.MILLISECONDS);
        return ResponseEntity.ok().body(new PropostaResponse(optional.get()));
    }

}
