package com.ugurcansandik.Avansas.controller;

import com.ugurcansandik.Avansas.dto.request.UpdateRequestDto;
import com.ugurcansandik.Avansas.facades.UserFacade;
import com.ugurcansandik.Avansas.validate.ValidateUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserFacade userFacade;
    private final ValidateUpdate validateUpdate;

    @DeleteMapping("/delete/{username}")
    public ResponseEntity<String> deleteUser(@PathVariable String username) {
        userFacade.deleteUser(username);
        return ResponseEntity.ok("Kullanıcı başarıyla silindi");
    }

    @PostMapping("/updateUser")
    public String updateUser(@ModelAttribute("user") UpdateRequestDto user, RedirectAttributes redirectAttributes) {
        String validationMessage = validateUpdate.validate(user);
        if (StringUtils.isEmpty(validationMessage)) {
            userFacade.updateUser(user);
            return "redirect:/auth/listing";
        } else {
            redirectAttributes.addFlashAttribute("message", validationMessage);
            return "redirect:/update/" + user.getUsername();
        }
    }

    @GetMapping("/update/{username}")
    public String showUpdateForm(Model model, @PathVariable String username) {
        model.addAttribute("user", userFacade.getUserByUsername(username));
        return "updateUser";
    }
}
