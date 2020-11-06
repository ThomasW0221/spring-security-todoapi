package io.twinterf.todowithjwtauth.models;

import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class UserDao {

    @Id
    private String userName;

    @Column
    @JsonIgnore
    private String password;

    public UserDao() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
