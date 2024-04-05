package com.ugurcansandik.Avansas.controller;

import com.ugurcansandik.Avansas.dto.request.RegisterRequestDto;
import com.ugurcansandik.Avansas.facades.UserFacade;
import com.ugurcansandik.Avansas.validate.ValidateRegister;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class AddUserController {
    private final UserFacade userFacade;
    private final ValidateRegister validateRegister;

    @GetMapping("/addUser")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new RegisterRequestDto());
        return "addUser";
    }

    @PostMapping("/addUser")
    public String addUser(@ModelAttribute("user") RegisterRequestDto user, RedirectAttributes redirectAttributes) {
        String validationMessage = validateRegister.validate(user);
        if (StringUtils.isEmpty(validationMessage)) {
            boolean signUpSuccess = userFacade.signUp(user);
            if (signUpSuccess) {
                redirectAttributes.addFlashAttribute("registrationSuccess", "Registration successful.");
                return "redirect:/auth/listing";
            } else {
                redirectAttributes.addFlashAttribute("message", "Username already exists or registration failed.");
                return "redirect:/addUser";
            }
        } else {
            redirectAttributes.addFlashAttribute("message", validationMessage);
            return "redirect:/addUser";
        }
    }
}
