package ua.dragun.phonebookapp.dao.user;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import ua.dragun.phonebookapp.entity.User;

import javax.transaction.Transactional;
import java.util.List;

@Profile("db")
@Repository
@Transactional
public class UserDaoDBImpl implements UserDao {

    private final SessionFactory sessionFactory;

    @Autowired
    public UserDaoDBImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Integer create(User user) {
        return (Integer) sessionFactory.getCurrentSession().save(user);
    }

    @Override
    public User view(String username) {
        return sessionFactory.getCurrentSession().get(User.class, username);
    }

    @Override
    public boolean update(User user) {
        sessionFactory.getCurrentSession().update(user);
        return true;
    }

    @Override
    public boolean remove(User user) {
        sessionFactory.getCurrentSession().delete(user);
        return true;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> findUsers() {
        return (List<User>) sessionFactory.getCurrentSession().createQuery("from User user").list();
    }

}
