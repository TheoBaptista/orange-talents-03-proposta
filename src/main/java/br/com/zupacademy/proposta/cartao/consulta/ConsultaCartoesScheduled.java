package br.com.zupacademy.proposta.cartao.consulta;

import br.com.zupacademy.proposta.cartao.Cartao;
import br.com.zupacademy.proposta.feign.CartaoFeignClient;
import br.com.zupacademy.proposta.proposta.AnaliseFinanceiraStatus;
import br.com.zupacademy.proposta.proposta.Proposta;
import br.com.zupacademy.proposta.proposta.PropostaRepository;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class ConsultaCartoesScheduled {

    private final PropostaRepository propostaRepository;
    private final CartaoFeignClient cartaoFeignClient;
    private final Logger logger = LoggerFactory.getLogger(ConsultaCartoesScheduled.class);

    public ConsultaCartoesScheduled(PropostaRepository propostaRepository, CartaoFeignClient cartaoFeignClient) {
        this.propostaRepository = propostaRepository;
        this.cartaoFeignClient = cartaoFeignClient;
    }

    @Scheduled(initialDelayString = "${tempo.inicial.consulta.cartao}", fixedDelayString = "${tempo.periodico.consulta.cartao}")
    private void consultaCartao() {

        List<Proposta> propostasElegiveisSemCartao = propostaRepository
                .findAllByCartaoIsNullAndAnaliseFinanceiraStatusEquals(AnaliseFinanceiraStatus.ELEGIVEL);

        for (Proposta proposta : propostasElegiveisSemCartao){
            try {

                ConsultaCartaoRequest request = new ConsultaCartaoRequest(proposta);
                ConsultaCartaoResponse consultaCartaoResponse = cartaoFeignClient.consultaCartao(request);
                Cartao cartao = consultaCartaoResponse.toModel();

                proposta.adicionaCartaoAProposta(cartao);

                propostaRepository.save(proposta);

                logger.info("Cartao com final {} relacionado a proposta {}",cartao.numeroCartaoOfuscado(),proposta.getId());

            }catch (FeignException e){
                logger.warn("Cartão da proposta {} ainda não foi processado",proposta.getId());
            }catch (Exception e ){
                logger.error("Erro: ",e);
            }
        }
    }

}