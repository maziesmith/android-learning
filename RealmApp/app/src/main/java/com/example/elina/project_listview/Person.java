package com.example.elina.project_listview;

import android.media.Image;

import io.realm.Realm;
import io.realm.RealmObject;

/**
 * Created by Elina on 13.10.2017.
 */

public class Person extends RealmObject {
    private String firstName, lastName, status, patronymic;
    private boolean flag = false;

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getStatus() {
        return status;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public Boolean getFlag() {
        return flag;
    }
}
