package com.hcns.training.entity;

import java.util.Objects;

public class Department {

    private Integer id;
    private String title;
    private String group;

    public Department(Integer id, String title, String group) {
        this.id = id;
        this.title = title;
        this.group = group;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

}
