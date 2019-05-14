package com.example.android.base.dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class User {
    @Id
    private long id;
    private String name;
    private String content;
    private String date;
    public String getDate() {
        return this.date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getContent() {
        return this.content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    @Generated(hash = 503642698)
    public User(long id, String name, String content, String date) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.date = date;
    }
    @Generated(hash = 586692638)
    public User() {
    }

}
