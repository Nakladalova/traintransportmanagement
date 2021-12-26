package org.but.feec.traintransportmanagement.api;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UserView {
    private StringProperty userIdString = new SimpleStringProperty();
    private LongProperty userId = new SimpleLongProperty();
    private StringProperty surname = new SimpleStringProperty();
    private StringProperty email = new SimpleStringProperty();
    private StringProperty password = new SimpleStringProperty();

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getPassword() {
        return password.get();
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public String getSurname() {
        return surname.get();
    }

    public StringProperty surnameProperty() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname.set(surname);
    }


    public String getUserIdString() {
        return userIdString.get();
    }

    public StringProperty userIdStringProperty() {
        return userIdString;
    }

    public void setUserIdString(String userIdString) {
        this.userIdString.set(userIdString);
    }


    public long getUserId() {
        return userId.get();
    }

    public LongProperty userIdProperty() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId.set(userId);
    }
}
