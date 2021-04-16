package br.com.zupacademy.proposta.feign;

import br.com.zupacademy.proposta.cartao.ConsultaCartaoRequest;
import br.com.zupacademy.proposta.cartao.ConsultaCartaoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "consultaCartaoFeignClient", url = "${consulta.cartao}")
public interface ConsultaCartaoFeignClient {

    @PostMapping(path = "/api/cartoes", consumes = "application/json")
     ConsultaCartaoResponse consultaCartao(@RequestBody ConsultaCartaoRequest request);
}
