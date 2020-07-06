package br.com.codenation.mappers;

import br.com.codenation.mappers.interfaces.EncodedMapping;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(componentModel = "spring")
public abstract class PasswordEncoderMapper {
    @Autowired
    protected  PasswordEncoder passwordEncoder;

    @EncodedMapping
    public String encode(String value) {
        return passwordEncoder.encode(value);
    }
}
