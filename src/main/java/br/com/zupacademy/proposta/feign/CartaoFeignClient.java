package br.com.zupacademy.proposta.feign;

import br.com.zupacademy.proposta.cartao.bloqueio.BloqueiaCartaoRequest;
import br.com.zupacademy.proposta.cartao.consulta.ConsultaCartaoRequest;
import br.com.zupacademy.proposta.cartao.consulta.ConsultaCartaoResponse;
import br.com.zupacademy.proposta.cartao.bloqueio.BloqueiaCartaoResponse;
import br.com.zupacademy.proposta.cartao.viagem.AvisoViagemRequest;
import br.com.zupacademy.proposta.cartao.viagem.AvisoViagemResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@FeignClient(name = "consultaCartaoFeignClient", url = "${servico.cartao}/api")
public interface CartaoFeignClient {

    @PostMapping(path = "/cartoes", consumes = "application/json")
     ConsultaCartaoResponse consultaCartao(@RequestBody @Valid ConsultaCartaoRequest request);

    @PostMapping(path = "/cartoes/{id}/bloqueios", consumes = "application/json")
    BloqueiaCartaoResponse bloqueiaCartao(@RequestBody @Valid BloqueiaCartaoRequest bloqueiaCartaoRequest, @PathVariable(value = "id") String numeroCartao);

    @PostMapping(path = "/cartoes/{id}/avisos", consumes = "application/json")
    AvisoViagemResponse notificarViagem(@RequestBody @Valid AvisoViagemRequest avisoViagemRequest, @PathVariable("id") String numeroCartao);

}
