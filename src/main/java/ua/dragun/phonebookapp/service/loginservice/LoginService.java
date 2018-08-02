package ua.dragun.phonebookapp.service.loginservice;

import ua.dragun.phonebookapp.entity.User;
import ua.dragun.phonebookapp.service.enums.Status;

public interface LoginService {

    User findByUsername(String username);
    Status create(String username, String password, String fullName);

}
