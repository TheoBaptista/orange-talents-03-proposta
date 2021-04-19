package br.com.zupacademy.proposta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableFeignClients
@SpringBootApplication
@EnableScheduling

public class PropostaApplication {

	public static void main(String[] args) {

		SpringApplication.run(PropostaApplication.class, args);

	}

}
