package ua.dragun.phonebookapp.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.dragun.phonebookapp.entity.Contact;
import ua.dragun.phonebookapp.entity.User;
import ua.dragun.phonebookapp.service.loginservice.LoginService;
import ua.dragun.phonebookapp.service.phonebookappservice.PhoneBookAppService;
import ua.dragun.phonebookapp.service.enums.Status;

@Controller
@PropertySource(value = {"classpath:application.properties"})
public class PhoneBookAppController {

    @Autowired
    private LoginService signService;
    @Autowired
    private PhoneBookAppService phoneBookAppService;

    @RequestMapping(value = "/phoneBookApp")
    public String phoneBookApp(HttpSession session, Model model) {
        if (session.getAttribute("username") != null) {
            User user = signService.findByUsername(session.getAttribute("username").toString());
            System.out.println(user);
            model.addAttribute("username", user.getUsername());
            model.addAttribute("contacts", user.getContacts());
            return "phonebook";
        } else {
            return "redirect:/";
        }
    }

    @RequestMapping(value = "/phoneBookApp/add")
    public String add(HttpSession session,
                      Model model) {
        if (session.getAttribute("username") != null) {
            User user = signService.findByUsername(session.getAttribute("username").toString());
            model.addAttribute("username", user.getUsername());
            return "adds";
        } else {
            return "redirect:/";
        }
    }


    @RequestMapping(value = "/phoneBookApp/addNew", method = RequestMethod.POST)
    public String addNew(@RequestParam String surname,
                         @RequestParam String firstName,
                         @RequestParam String patronymic,
                         @RequestParam String mobilePhone,
                         @RequestParam String homePhone,
                         @RequestParam String address,
                         @RequestParam String email,
                         HttpSession session,
                         RedirectAttributes redirectAttributes) {
        User user = signService.findByUsername(session.getAttribute("username").toString());
        Contact contact = new Contact(surname, firstName, patronymic, mobilePhone, homePhone, address, email, user);
        Status status = phoneBookAppService.add(contact, user.getUsername());
        switch (status) {
            case INCORRECT_SURNAME:
                redirectAttributes.addFlashAttribute("message", "Surname must contain at least 4 symbols");
                return "redirect:/phoneBookApp/add";
            case INCORRECT_FIRSTNAME:
                redirectAttributes.addFlashAttribute("message", "First Name must contain at least 4 symbols");
                return "redirect:/phoneBookApp/add";
            case INCORRECT_PATRONYMIC:
                redirectAttributes.addFlashAttribute("message", "Patronymic must contain at least 4 symbols");
                return "redirect:/phoneBookApp/add";
            case INCORRECT_MOBILE:
                redirectAttributes.addFlashAttribute("message", "Mobile phone must have format: +XXX(XX)XXX-XX-XX");
                return "redirect:/phoneBookApp/add";
            case INCORRECT_HOME:
                redirectAttributes.addFlashAttribute("message", "Home phone must have format: +XXX(XX)XXX-XX-XX");
                return "redirect:/phoneBookApp/add";
            case INCORRECT_EMAIL:
                redirectAttributes.addFlashAttribute("message", "Email must have format: test@example.com");
                return "redirect:/phoneBookApp/add";
            case SUCCESS:
                redirectAttributes.addFlashAttribute("message", "New entry added");
                return "redirect:/phoneBookApp/add";
        }
        return "redirect:/phoneBookApp/add";
    }


    @RequestMapping(value = "/phoneBookApp/edit/{contactId}")
    public String edit(HttpSession session,
                       @PathVariable String contactId,
                       Model model) {
        if (session.getAttribute("username") != null) {
            model.addAttribute("username", session.getAttribute("username").toString());
            model.addAttribute("contactId", contactId);
            return "edits";
        }
        return "redirect:/";
    }


    @RequestMapping(value = "/phoneBookApp/editContact", method = RequestMethod.POST)
    public String editContact(@RequestParam String contactId,
                              @RequestParam String surname,
                              @RequestParam String firstName,
                              @RequestParam String patronymic,
                              @RequestParam String mobilePhone,
                              @RequestParam String homePhone,
                              @RequestParam String address,
                              @RequestParam String email,
                              HttpSession session,
                              RedirectAttributes redirectAttributes) {
        User user = signService.findByUsername(session.getAttribute("username").toString());
        Contact contact = new Contact(Integer.parseInt(contactId), surname, firstName, patronymic, mobilePhone, homePhone, address, email, user);
        Status status = phoneBookAppService.edit(contact, user.getUsername());
        switch (status) {
            case INCORRECT_SURNAME:
                redirectAttributes.addFlashAttribute("message", "Surname must contain at least 4 symbols");
                return "redirect:/phoneBookApp/edit/" + contactId;
            case INCORRECT_FIRSTNAME:
                redirectAttributes.addFlashAttribute("message", "First Name must contain at least 4 symbols");
                return "redirect:/phoneBookApp/edit/" + contactId;
            case INCORRECT_PATRONYMIC:
                redirectAttributes.addFlashAttribute("message", "Patronymic must contain at least 4 symbols");
                return "redirect:/phoneBookApp/edit/" + contactId;
            case INCORRECT_MOBILE:
                redirectAttributes.addFlashAttribute("message", "Mobile phone must have format: +XXX(XX)XXX-XX-XX");
                return "redirect:/phoneBookApp/edit/" + contactId;
            case INCORRECT_HOME:
                redirectAttributes.addFlashAttribute("message", "Home phone must have format: +XXX(XX)XXX-XX-XX");
                return "redirect:/phoneBookApp/edit/" + contactId;
            case INCORRECT_EMAIL:
                redirectAttributes.addFlashAttribute("message", "E-mail must have format: test@example.com");
                return "redirect:/phoneBookApp/edit/" + contactId;
            case SUCCESS:
                redirectAttributes.addFlashAttribute("message", "Contact Changed");
                return "redirect:/phoneBookApp/edit/" + contactId;
        }
        return "redirect:/phoneBookApp/edit/" + contactId;
    }


    @RequestMapping(value = "/phoneBookApp/delete", method = RequestMethod.POST)
    public @ResponseBody
    String delete(@RequestParam String contactId,
                  HttpSession session,
                  Model model) {
        Integer id = Integer.parseInt(contactId);
        return phoneBookAppService.delete(id, session.getAttribute("username").toString())
                .toString();
    }

    @RequestMapping(value = "/logOut")
    public String logOut(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
