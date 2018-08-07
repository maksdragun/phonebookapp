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
    public String singIn(@RequestParam String username,
                         @RequestParam String password,
                         HttpSession session,
                         RedirectAttributes redirectAttributes) {
        User user = loginService.findByUsername(username);
        if(user == null || !user.getPassword().equals(password)) {
            redirectAttributes.addFlashAttribute("message", "Wrong Username or Password!");
            return "redirect:/";
        } else {
            session.setMaxInactiveInterval(900);
            session.setAttribute("username", username);
            return "redirect:/phoneBookApp";
        }
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@RequestParam String username,
                      @RequestParam String password,
                      @RequestParam String fullName,
                      HttpSession session,
                      RedirectAttributes redirectAttributes) {
        Status status = loginService.create(username, password, fullName);
        switch(status) {
            case ALREADY_EXISTS:
                redirectAttributes.addFlashAttribute("message", "This Login already exists!");
                return "redirect:/";
            case INCORRECT_USERNAME:
                redirectAttributes.addFlashAttribute("message", "Login must contain only English symbols (at least 3)");
                return "redirect:/";
            case INCORRECT_PASSWORD:
                redirectAttributes.addFlashAttribute("message", "Password must contain only numbers and English symbols (at least 5)");
                return "redirect:/";
            case INCORRECT_FULLNAME:
                redirectAttributes.addFlashAttribute("message", "Full Name must contain at least 5 symbols");
                return "redirect:/";
            case SUCCESS:
                session.setMaxInactiveInterval(900);
                session.setAttribute("username", username);
                return "redirect:/phoneBookApp";
        }
        return "redirect:/";
    }
}
