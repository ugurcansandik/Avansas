package com.ugurcansandik.Avansas.controller;

import com.ugurcansandik.Avansas.facades.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class UserListingController {

    private final UserFacade userFacade;

    @GetMapping("/auth/listing")
    public ModelAndView  showRegistrationForm() {
        ModelAndView map = new ModelAndView("listing");
        map.addObject("users", userFacade.getAllUsers());
        return map;
    }
}
