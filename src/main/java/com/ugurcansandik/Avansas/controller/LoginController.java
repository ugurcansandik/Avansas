package com.ugurcansandik.Avansas.controller;

import com.ugurcansandik.Avansas.dto.request.LoginRequestDto;
import com.ugurcansandik.Avansas.facades.UserFacade;
import jakarta.servlet.http.HttpServletResponse;
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
public class LoginController {
    private final UserFacade userFacade;

    @PostMapping("/login")
    public String login(@ModelAttribute("user") LoginRequestDto user, RedirectAttributes redirectAttributes, HttpServletResponse response) {
        String message = userFacade.signIn(user,response);
        if (!StringUtils.isEmpty(message)) {
            redirectAttributes.addFlashAttribute("message", message);
            return "redirect:/login";
        }
        return "redirect:/auth/listing";
    }

    @GetMapping("/login")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new LoginRequestDto());
        return "login";
    }
}
