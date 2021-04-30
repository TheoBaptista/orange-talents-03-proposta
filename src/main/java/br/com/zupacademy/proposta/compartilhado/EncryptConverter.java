package br.com.zupacademy.proposta.compartilhado;

import org.springframework.security.crypto.encrypt.Encryptors;

import javax.persistence.AttributeConverter;

public class EncryptConverter implements AttributeConverter<String,String> {


    private final String password = "password";
    private final String salt = "5c0744940b5c369b";

    @Override
    public String convertToDatabaseColumn(String attribute) {
        return Encryptors.queryableText(this.password, this.salt).encrypt(attribute);
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        return Encryptors.queryableText(this.password,this.salt).decrypt(dbData);
    }
}
