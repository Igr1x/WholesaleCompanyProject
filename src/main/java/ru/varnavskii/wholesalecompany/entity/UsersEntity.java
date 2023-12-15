package ru.varnavskii.wholesalecompany.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsersEntity {
    private String login;
    private String password;

    public UsersEntity(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Override
    public String toString() {
        return "UsersEntity{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
