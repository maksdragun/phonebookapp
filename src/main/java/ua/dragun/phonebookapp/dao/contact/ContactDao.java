package ua.dragun.phonebookapp.dao.contact;

import ua.dragun.phonebookapp.entity.Contact;

import java.util.List;

public interface ContactDao {

    Integer create(Contact contact);

    Contact view(Integer id);

    boolean update(Contact contact);

    boolean remove(Contact contact);

    List<Contact> findContacts();
}
