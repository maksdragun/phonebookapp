package ua.dragun.phonebookapp.service.loginservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.dragun.phonebookapp.dao.user.UserDao;
import ua.dragun.phonebookapp.entity.User;
import ua.dragun.phonebookapp.service.enums.Status;

import java.util.List;
import java.util.regex.Pattern;

@Service
public class LoginServiceImpl implements LoginService {

    private final Pattern USERNAME_PATTERN = Pattern.compile("^[A-Za-z]{3,}$");
    private final Pattern PASSWORD_PATTERN = Pattern.compile("^[A-Za-z0-9]{5,}$");

    private UserDao userDao;

    @Autowired
    public LoginServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User findByUsername(String username) {
        List<User> list = userDao.findUsers();
        for(User users : list) {
            if(users.getUsername().equalsIgnoreCase(username))
                return users;
        }
        return null;
    }

    @Override
    public Status create(String username, String password, String fullName) {
        List<User> list = userDao.findUsers();
        for(User users : list) {
            if(users.getUsername().equalsIgnoreCase(username))
                return Status.ALREADY_EXISTS;
        }
        if( !USERNAME_PATTERN.matcher(username).matches() ) {
            return Status.INCORRECT_USERNAME;
        } else if ( !PASSWORD_PATTERN.matcher(password).matches() ) {
            return Status.INCORRECT_PASSWORD;
        } else if ( fullName.length() < 5 ) {
            return Status.INCORRECT_FULLNAME;
        } else {
            User user = new User(username, password, fullName);
            userDao.create(user);
            return Status.SUCCESS;
        }
    }
}