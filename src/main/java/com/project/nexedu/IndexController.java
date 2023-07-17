package com.project.nexedu;

import com.project.nexedu.config.PrincipalDetails;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@AllArgsConstructor
@Controller
public class IndexController {

    @GetMapping({"/"})
    public String index(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
        if (principalDetails != null) {
            model.addAttribute("username", principalDetails.getUser().getUsername());
            model.addAttribute("nickname", principalDetails.getUser().getNickname());
        }

        return "index";
    }
}
