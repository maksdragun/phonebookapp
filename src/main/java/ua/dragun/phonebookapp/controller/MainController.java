package ua.dragun.phonebookapp.controller;

import javax.servlet.http.HttpSession;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@PropertySource(value = {"classpath:file.properties"})
public class MainController {

    @RequestMapping("/*")
    public String hello(HttpSession session) {
        return "index";
    }
}