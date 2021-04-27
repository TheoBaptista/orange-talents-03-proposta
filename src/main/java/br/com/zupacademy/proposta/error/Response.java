package br.com.zupacademy.proposta.error;

import org.springframework.http.HttpStatus;

public class Response {

    private final Boolean sucesso;

    private final HttpStatus status;

    private final String message;

    public Response(Boolean sucesso, HttpStatus status, String message) {
        this.sucesso = sucesso;
        this.status = status;
        this.message = message;
    }

    public Boolean isSuccess(){
        return this.sucesso;
    }

    public HttpStatus getStatus(){
        return this.status;
    }

    public String getMessage(){
        return this.message;
    }
}
