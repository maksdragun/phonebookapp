package ua.dragun.phonebookapp.entity;

import org.hibernate.annotations.GenericGenerator;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import ua.dragun.phonebookapp.entity.Contact;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Integer id;

    @NotNull
    @Size(min = 3)
    @Column(name = "username", unique = true)
    private String username;

    @NotNull
    @Size(min = 5)
    @Column(name = "password")
    private String password;

    @NotNull
    @Size(min = 5)
    @Column(name = "fullName")
    private String fullName;


    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<Contact> contacts = new HashSet<>();

    public User() {
    }

    public User(String username, String password, String fullName) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
    }

    public User(String username, String password, String fullName, Set<Contact> contacts) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.contacts = contacts;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Set<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(Set<Contact> contacts) {
        this.contacts = contacts;
    }

    @Override
    public String toString() {
        return "User [id=" + id
                + ", username=" + username
                + ", password=" + password
                + ", fullName=" + fullName
                + ", contacts=" + contacts + "]";
    }


}
