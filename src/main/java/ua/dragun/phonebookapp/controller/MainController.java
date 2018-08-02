package ua.dragun.phonebookapp.controller;

import javax.servlet.http.HttpSession;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@PropertySource(value = {"classpath:application.properties"})
public class MainController {

    @RequestMapping("/*")
    public String main(HttpSession session) {
        return "index";
    }
}