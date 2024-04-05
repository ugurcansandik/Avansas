package com.ugurcansandik.Avansas.validate;

import com.ugurcansandik.Avansas.dto.request.RegisterRequestDto;
import com.ugurcansandik.Avansas.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.regex.Pattern;
import java.util.stream.Stream;

@Service
@Slf4j
@RequiredArgsConstructor
public class ValidateRegister extends AbstractValidation{
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\+(?:[0-9] ?){6,14}[0-9]$");
    private static final Pattern BIRTHDAY_PATTERN = Pattern.compile("[1-9][0-9][0-9]{2}-([0][1-9]|[1][0-2])-([1-2][0-9]|[0][1-9]|[3][0-1])");

    private final UserService userService;

    @Override
    public <T> String validate(T object) {
        if (object instanceof RegisterRequestDto registerDto) {
            if (this.nullCheck(registerDto)) {
                return "All fields must be filled.";
            }

            if (!EMAIL_PATTERN.matcher(registerDto.getEmail()).matches()) {
                return "Enter a valid email format.";
            }

            if (userService.getUserByUsername(registerDto.getUsername()).isPresent()) {
                return "A user with this username already exists.";
            }

            if (!PHONE_PATTERN.matcher(registerDto.getPhone()).matches()) {
                return "Enter a valid phone number format.";
            }

            if (!BIRTHDAY_PATTERN.matcher(registerDto.getBirthday()).matches()) {
                return "Enter a valid date of birth format.";
            }
            if (!PASSWORD_PATTERN.matcher(registerDto.getPassword()).matches()) {
                return "Password must contain at least one digit, one lowercase letter, one uppercase letter, one special character (@#$%^&+=!), be at least 8 characters long, and not contain spaces.";
            }
        }
        return null;
    }

    @Override
    public <T> boolean nullCheck(T object) {
        RegisterRequestDto registerRequestDto = (RegisterRequestDto) object;
        return Stream.of(
                registerRequestDto.getBirthday(),
                registerRequestDto.getEmail(),
                registerRequestDto.getPhone(),
                registerRequestDto.getFirstName(),
                registerRequestDto.getLastName(),
                registerRequestDto.getUsername(),
                registerRequestDto.getPassword()
        ).anyMatch(StringUtils::isEmpty);
    }
}
