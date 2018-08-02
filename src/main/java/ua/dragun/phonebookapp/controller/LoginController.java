package ua.dragun.phonebookapp.controller;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.dragun.phonebookapp.entity.User;
import ua.dragun.phonebookapp.service.enums.Status;
import ua.dragun.phonebookapp.service.loginservice.LoginService;

@Controller
public class LoginController {

    private LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @RequestMapping(value = "/signIn", method = RequestMethod.POST)
    public String singIn(@RequestParam String login,
                         @RequestParam String password,
                         HttpSession session,
                         RedirectAttributes redirectAttributes) {
        User user = loginService.findByUsername(login);
        if(user == null || !user.getPassword().equals(password)) {
            redirectAttributes.addFlashAttribute("answer", "Wrong Login or Password!");
            return "redirect:/";
        } else {
            session.setMaxInactiveInterval(900);
            session.setAttribute("login", login);
            return "redirect:/phoneBook";
        }
    }

    @RequestMapping(value = "/signUp")
    public String signUp() {
        return "signUp";
    }

    @RequestMapping(value = "/reg", method = RequestMethod.POST)
    public String reg(@RequestParam String login,
                      @RequestParam String password,
                      @RequestParam String fullName,
                      HttpSession session,
                      RedirectAttributes redirectAttributes) {
        Status status = loginService.create(login, password, fullName);
        switch(status) {
            case ALREADY_EXISTS:
                redirectAttributes.addFlashAttribute("answer", "This Login Already Exists!");
                return "redirect:/signUp";
            case INCORRECT_USERNAME:
                redirectAttributes.addFlashAttribute("answer", "Login must contain only English symbols (at least 3)");
                return "redirect:/signUp";
            case INCORRECT_PASSWORD:
                redirectAttributes.addFlashAttribute("answer", "Password must contain only numbers and English symbols (at least 5)");
                return "redirect:/signUp";
            case INCORRECT_FULLNAME:
                redirectAttributes.addFlashAttribute("answer", "Full Name must contain at least 5 symbols");
                return "redirect:/signUp";
            case SUCCESS:
                session.setMaxInactiveInterval(900);
                session.setAttribute("login", login);
                return "redirect:/phoneBook";
        }
        return "redirect:/signUp";
    }

}
