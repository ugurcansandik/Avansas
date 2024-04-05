package com.ugurcansandik.Avansas.validate;

import com.ugurcansandik.Avansas.dto.request.UpdateRequestDto;
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
public class ValidateUpdate extends AbstractValidation{
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\+(?:[0-9] ?){6,14}[0-9]$");
    private static final Pattern BIRTHDAY_PATTERN = Pattern.compile("[1-9][0-9][0-9]{2}-([0][1-9]|[1][0-2])-([1-2][0-9]|[0][1-9]|[3][0-1])");

    private final UserService userService;

    @Override
    public <T> String validate(T object) {
        if (object instanceof UpdateRequestDto updateRequestDto) {
            if (this.nullCheck(updateRequestDto)) {
                return "All fields must be filled.";
            }

            if (!EMAIL_PATTERN.matcher(updateRequestDto.getEmail()).matches()) {
                return "Enter a valid email format.";
            }

            if (!PHONE_PATTERN.matcher(updateRequestDto.getPhone()).matches()) {
                return "Enter a valid phone number format.";
            }

            if (!BIRTHDAY_PATTERN.matcher(updateRequestDto.getBirthday()).matches()) {
                return "Enter a valid date of birth format.";
            }
        }
        return null;
    }

    @Override
    public <T> boolean nullCheck(T object) {
        UpdateRequestDto updateRequestDto = (UpdateRequestDto) object;
        return Stream.of(
                updateRequestDto.getBirthday(),
                updateRequestDto.getEmail(),
                updateRequestDto.getPhone(),
                updateRequestDto.getFirstName(),
                updateRequestDto.getLastName(),
                updateRequestDto.getUsername()
        ).anyMatch(StringUtils::isEmpty);
    }
}
