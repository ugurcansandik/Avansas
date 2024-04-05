package com.ugurcansandik.Avansas.validate;

import com.ugurcansandik.Avansas.dto.request.LoginRequestDto;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.stream.Stream;

@Service
public class ValidateLogin extends AbstractValidation{

    @Override
    public <T> String validate(T object) {
        if(object instanceof LoginRequestDto loginRequestDto){
            if (this.nullCheck(loginRequestDto)) {
                return "All fields must be filled.";
            }
        }
        return null;
    }

    @Override
    public <T> boolean nullCheck(T object) {
        LoginRequestDto loginRequestDto = (LoginRequestDto) object;
        return Stream.of(
                loginRequestDto.getUsername(),
                loginRequestDto.getPassword()
        ).anyMatch(StringUtils::isEmpty);
    }
}
