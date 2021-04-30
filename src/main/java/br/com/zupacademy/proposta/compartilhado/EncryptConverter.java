package br.com.zupacademy.proposta.compartilhado;

import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.keygen.KeyGenerators;

import javax.persistence.AttributeConverter;

public class EncryptConverter implements AttributeConverter<String,String> {


    private final String password = "password";
    private final String salt = KeyGenerators.string().generateKey();

    @Override
    public String convertToDatabaseColumn(String attribute) {
        return Encryptors.text(this.password, this.salt).encrypt(attribute);
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        return Encryptors.text(this.password,this.salt).decrypt(dbData);
    }
}
