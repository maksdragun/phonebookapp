package ua.dragun.phonebookapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import ua.dragun.phonebookapp.entity.User;


@Entity
@Table(name = "contacts")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Integer id;

    @NotNull
    @Column(name = "surname")
    @Size(min = 4)
    private String surname;

    @NotNull
    @Column(name = "firstName")
    @Size(min = 4)
    private String firstName;

    @NotNull
    @Column(name = "patronymic")
    @Size(min = 4)
    private String patronymic;

    @NotNull
    @Column(name = "mobilePhone")
    private String mobilePhone;

    @Column(name = "homePhone")
    private String homePhone;

    @Column(name = "address")
    private String address;

    @Column(name = "email")
    private String email;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    public Contact() {
    }

    public Contact(int id, String surname, String firstName, String patronymic, String mobilePhone, String homePhone,
                   String address, String email, User user) {
        this.id = id;
        this.surname = surname;
        this.firstName = firstName;
        this.patronymic = patronymic;
        this.mobilePhone = mobilePhone;
        this.homePhone = homePhone;
        this.address = address;
        this.email = email;
        this.user = user;
    }

    public Contact(String surname, String firstName, String patronymic, String mobilePhone, String homePhone,
                   String address, String email, User user) {
        this.surname = surname;
        this.firstName = firstName;
        this.patronymic = patronymic;
        this.mobilePhone = mobilePhone;
        this.homePhone = homePhone;
        this.address = address;
        this.email = email;
        this.user = user;
    }


    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Contact [id=" + id
                + ", surname=" + surname
                + ", firstName=" + firstName
                + ", patronymic=" + patronymic
                + ", mobilePhone=" + mobilePhone
                + ", homePhone=" + homePhone
                + ", address=" + address
                + ", email="+ email + "]";
    }

}
