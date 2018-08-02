package ua.dragun.phonebookapp.dao.contact;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import ua.dragun.phonebookapp.entity.Contact;

import javax.transaction.Transactional;
import java.util.List;

@Profile("db")
@Repository
@Transactional
public class ContactDaoDBImpl implements ContactDao {

    private final SessionFactory sessionFactory;

    @Autowired
    public ContactDaoDBImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Integer create(Contact contact) {
        return (Integer) sessionFactory.getCurrentSession().save(contact);
    }

    @Override
    public Contact view(Integer id) {
        return sessionFactory.getCurrentSession().get(Contact.class, id);
    }

    @Override
    public boolean update(Contact contact) {
        sessionFactory.getCurrentSession().update(contact);
        return true;
    }

    @Override
    public boolean remove(Contact contact) {
        sessionFactory.getCurrentSession().delete(contact);
        return true;
    }


    @Override
    @SuppressWarnings("unchecked")
    public List<Contact> findContacts() {
        return (List<Contact>) sessionFactory.getCurrentSession().createQuery("from Contact client").list();
    }

}
