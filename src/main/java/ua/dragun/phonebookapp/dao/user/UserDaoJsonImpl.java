package ua.dragun.phonebookapp.dao.user;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import ua.dragun.phonebookapp.entity.User;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Profile("json")
@Repository
public class UserDaoJsonImpl implements UserDao {

    private File jsonFile;

    private ObjectMapper mapper;

    @Autowired
    public UserDaoJsonImpl(File jsonFile) {
        this.jsonFile = jsonFile;
    }

    @Override
    public Integer create(User user) {
        List<User> users = findUsers();
        int id;
        if (users.size() == 0) {
            id = 1;
        } else {
            id = users.get(users.size() - 1).getId() + 1;
        }
        user.setId(id);
        users.add(user);
        writeToFile(users);
        return id;
    }

    @Override
    public User view(String username) {
        List<User> users = findUsers();
        for (User user : users) {
            if (user.getUsername().equals(username))
                return user;
        }
        return null;
    }

    @Override
    public boolean update(User user) {
        List<User> users = findUsers();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(user.getUsername())) {
                users.set(i, user);
                writeToFile(users);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean remove(User user) {
        List<User> users = findUsers();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(user.getUsername())) {
                users.remove(i);
                writeToFile(users);
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings("finally")
    @Override
    public List<User> findUsers() {
        if (mapper == null) {
            mapper = new ObjectMapper();
        }
        try {
            return mapper.readValue(jsonFile, new TypeReference<List<User>>() {});
        } catch (Exception e) {
            throw new RuntimeException("Error parsing the JSON file, please check your syntax", e);
        }
    }

    private void writeToFile(List<User> users) {
        if (mapper == null) {
            mapper = new ObjectMapper();
        }
        try {
            mapper.writeValue(jsonFile, users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
