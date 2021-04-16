package br.com.zupacademy.proposta.feign;

import br.com.zupacademy.proposta.proposta.ConsultaPropostaRequest;
import br.com.zupacademy.proposta.proposta.ConsultaPropostaResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "consultaRestricaoSolicitanteFeignClient", url = "${consulta.restricao.financeira}")
public interface ConsultaRestricaoFinanceiraSolicitanteFeignClient {

    @PostMapping(path = "/api/solicitacao", consumes = "application/json")
    ConsultaPropostaResponse consultaRestricaoFinanceiraSolicitante(@RequestBody ConsultaPropostaRequest request);

}
