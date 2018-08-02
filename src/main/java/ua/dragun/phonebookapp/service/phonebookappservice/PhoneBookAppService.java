package ua.dragun.phonebookapp.service.phonebookappservice;

import ua.dragun.phonebookapp.entity.Contact;
import ua.dragun.phonebookapp.service.enums.Status;

public interface PhoneBookAppService {
    Status add(Contact contact, String userName);
    Status edit(Contact contact, String userName);
    Integer delete(Integer contactId, String userName);
}