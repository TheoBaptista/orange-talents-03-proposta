package br.com.zupacademy.proposta.restricaoFinanceira;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "consultaRestricaoSolicitanteFeignClient", url = "${solicitacao.restricao}")
public interface ConsultaRestricaoSolicitanteFeignClient {

    @PostMapping(value = "/api/solicitacao", consumes = "application/json")
    ConsultaRestricaoSolicitanteResponse retornaConsultaFinanceiraSolicitante(@RequestBody ConsultaRestricaoSolicitanteRequest request);

}
