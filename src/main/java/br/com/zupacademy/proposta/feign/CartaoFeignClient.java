package br.com.zupacademy.proposta.feign;

import br.com.zupacademy.proposta.cartao.bloqueio.BloqueiaCartaoRequest;
import br.com.zupacademy.proposta.cartao.consulta.ConsultaCartaoRequest;
import br.com.zupacademy.proposta.cartao.consulta.ConsultaCartaoResponse;
import br.com.zupacademy.proposta.cartao.bloqueio.BloqueiaCartaoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "consultaCartaoFeignClient", url = "${servico.cartao}/api")
public interface CartaoFeignClient {

    @PostMapping(path = "/cartoes", consumes = "application/json")
     ConsultaCartaoResponse consultaCartao(@RequestBody ConsultaCartaoRequest request);

    @PostMapping(path = "/cartoes/{id}/bloqueios", consumes = "application/json")
    BloqueiaCartaoResponse bloqueiaCartao(@RequestBody BloqueiaCartaoRequest bloqueiaCartaoRequest, @PathVariable("id") String idCartao);

}
