package br.com.zupacademy.proposta.proposta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropostaRepository extends JpaRepository<Proposta, String> {

    boolean existsByDocumento(String documento);

    List<Proposta> findAllByCartaoIsNullAndAnaliseFinanceiraStatusEquals(AnaliseFinanceiraStatus status);

}
