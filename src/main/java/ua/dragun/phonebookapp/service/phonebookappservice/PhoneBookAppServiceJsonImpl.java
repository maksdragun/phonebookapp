package ua.dragun.phonebookapp.service.phonebookappservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ua.dragun.phonebookapp.dao.user.UserDao;
import ua.dragun.phonebookapp.entity.Contact;
import ua.dragun.phonebookapp.entity.User;
import ua.dragun.phonebookapp.service.enums.Status;

import java.util.Set;
import java.util.regex.Pattern;

@Profile("json")
@Service
public class PhoneBookAppServiceJsonImpl implements PhoneBookAppService {

    private final Pattern PHONE_PATTERN = Pattern.compile("^\\+\\d{3}\\(\\d{2}\\)\\d{7}$");
    private final Pattern EMAIL_PATTERN = Pattern.compile("^([a-z0-9_-]+\\.)*[a-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}$");

    private final UserDao userDao;

    @Autowired
    public PhoneBookAppServiceJsonImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public Status add(Contact contact, String userName) {
        if (contact.getSurname().length() < 4) {
            return Status.INCORRECT_SURNAME;
        } else if (contact.getFirstName().length() < 4) {
            return Status.INCORRECT_FIRSTNAME;
        } else if (contact.getPatronymic().length() < 4) {
            return Status.INCORRECT_PATRONYMIC;
        } else if (!PHONE_PATTERN.matcher(contact.getMobilePhone()).matches()) {
            return Status.INCORRECT_MOBILE;
        } else if (!contact.getHomePhone().isEmpty() && !PHONE_PATTERN.matcher(contact.getHomePhone()).matches()) {
            return Status.INCORRECT_HOME;
        } else if (!contact.getEmail().isEmpty() && !EMAIL_PATTERN.matcher(contact.getEmail()).matches()) {
            return Status.INCORRECT_EMAIL;
        } else {
            User user = userDao.view(userName);
            contact.setId(user.getContacts().size() + 1);
            user.getContacts().add(contact);
            userDao.update(user);
            return Status.SUCCESS;
        }
    }

    @Override
    public Status edit(Contact contact, String userName) {
        if (contact.getSurname().length() < 4) {
            return Status.INCORRECT_SURNAME;
        } else if (contact.getFirstName().length() < 4) {
            return Status.INCORRECT_FIRSTNAME;
        } else if (contact.getPatronymic().length() < 4) {
            return Status.INCORRECT_PATRONYMIC;
        } else if (!PHONE_PATTERN.matcher(contact.getMobilePhone()).matches()) {
            return Status.INCORRECT_MOBILE;
        } else if (!contact.getHomePhone().isEmpty() && !PHONE_PATTERN.matcher(contact.getHomePhone()).matches()) {
            return Status.INCORRECT_HOME;
        } else if (!contact.getEmail().isEmpty() && !EMAIL_PATTERN.matcher(contact.getEmail()).matches()) {
            return Status.INCORRECT_EMAIL;
        } else {
            User user = userDao.view(userName);
            Set<Contact> set = user.getContacts();
            user.getContacts().add(contact);
            for (Contact contactSet : set) {
                if (contactSet.getId().intValue() == contact.getId().intValue()) {
                    set.remove(contactSet);
                    set.add(contact);
                    user.setContacts(set);
                    userDao.update(user);
                    break;
                }
            }
            return Status.SUCCESS;
        }
    }

    @Override
    public Integer delete(Integer id, String userName) {
        User user = userDao.view(userName);
        Set<Contact> set = user.getContacts();
        for (Contact contacts : set) {
            if (contacts.getId().intValue() == id.intValue()) {
                set.remove(contacts);
                user.setContacts(set);
                userDao.update(user);
                return contacts.getId();
            }
        }
        return -1;
    }
}
