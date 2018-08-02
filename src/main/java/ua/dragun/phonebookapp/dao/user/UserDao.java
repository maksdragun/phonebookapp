package ua.dragun.phonebookapp.dao.user;

import ua.dragun.phonebookapp.entity.User;

import java.util.List;

public interface UserDao {

    Integer create(User user);

    User view(String username);

    boolean update(User user);

    boolean remove(User user);

    List<User> findUsers();
}