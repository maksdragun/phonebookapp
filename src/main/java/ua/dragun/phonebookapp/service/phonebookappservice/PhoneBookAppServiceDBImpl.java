package ua.dragun.phonebookapp.service.phonebookappservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ua.dragun.phonebookapp.dao.contact.ContactDao;
import ua.dragun.phonebookapp.entity.Contact;
import ua.dragun.phonebookapp.service.enums.Status;

import java.util.regex.Pattern;

@Profile("db")
@Service
public class PhoneBookAppServiceDBImpl implements PhoneBookAppService {

    private final Pattern PHONE_PATTERN = Pattern.compile("^\\+\\d{3}\\(\\d{2}\\)\\d{7}$");
    private final Pattern EMAIL_PATTERN = Pattern.compile("^([a-z0-9_-]+\\.)*[a-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}$");

    private ContactDao contactDao;

    @Autowired
    public PhoneBookAppServiceDBImpl(ContactDao contactDao) {
        this.contactDao = contactDao;
    }


    @Override
    public Status add(Contact contact, String userName) {
        if(contact.getSurname().length() < 4) {
            return Status.INCORRECT_SURNAME;
        } else if (contact.getFirstName().length() < 4) {
            return Status.INCORRECT_FIRSTNAME;
        } else if (contact.getPatronymic().length() < 4) {
            return Status.INCORRECT_PATRONYMIC;
        } else if ( !PHONE_PATTERN.matcher(contact.getMobilePhone()).matches() ) {
            return Status.INCORRECT_MOBILE;
        } else if( !contact.getHomePhone().isEmpty() && !PHONE_PATTERN.matcher(contact.getHomePhone()).matches()) {
            return Status.INCORRECT_HOME;
        } else if(!contact.getEmail().isEmpty() && !EMAIL_PATTERN.matcher(contact.getEmail()).matches()) {
            return Status.INCORRECT_EMAIL;
        } else {
            contactDao.create(contact);
            return Status.SUCCESS;
        }
    }

    @Override
    public Status edit(Contact contact, String userName) {
        if(contact.getSurname().length() < 4) {
            return Status.INCORRECT_SURNAME;
        } else if (contact.getFirstName().length() < 4) {
            return Status.INCORRECT_FIRSTNAME;
        } else if (contact.getPatronymic().length() < 4) {
            return Status.INCORRECT_PATRONYMIC;
        } else if ( !PHONE_PATTERN.matcher(contact.getMobilePhone()).matches() ) {
            return Status.INCORRECT_MOBILE;
        } else if( !contact.getHomePhone().isEmpty() && !PHONE_PATTERN.matcher(contact.getHomePhone()).matches()) {
            return Status.INCORRECT_HOME;
        } else if(!contact.getEmail().isEmpty() && !EMAIL_PATTERN.matcher(contact.getEmail()).matches()) {
            return Status.INCORRECT_EMAIL;
        } else {
            contactDao.update(contact);
            return Status.SUCCESS;
        }
    }

    @Override
    public Integer delete(Integer id, String userName) {
        Contact contact = contactDao.view(id);
        if(contact != null) {
            contactDao.remove(contact);
            return contact.getId();
        }
        return -1;
    }

}