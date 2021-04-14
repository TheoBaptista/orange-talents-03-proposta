package br.com.zupacademy.proposta.error;

import java.util.ArrayList;
import java.util.List;

public class FieldErrors {
    private List<String> messages = new ArrayList<>();

    public FieldErrors(List<String> messages) {
        this.messages = messages;
    }
    public FieldErrors(String message) {
        this.messages.add(message);
    }
    public List<String> getMessages() {
        return messages;
    }

}
