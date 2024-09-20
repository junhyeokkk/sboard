package com.sboard.controller.User;

import com.sboard.Service.TermsService;
import com.sboard.dto.TermsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class TermsController {

    private final TermsService termsService;

    @GetMapping (value = "/user/terms")
    public String terms(Model model){
        List<TermsDTO> terms = termsService.selectAll();

        model.addAttribute("terms", terms);

        return "/user/terms";
    }

}
