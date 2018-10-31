package com.zejor.bean;

import java.util.List;

/**
 * 联系人的实体类
 */

public class ContactsBean {
    private String name;
    private List<String> phone;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getPhone() {
        return phone;
    }

    public void setPhone(List<String> phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "ContactsBean{" +
                "name='" + name + '\'' +
                ", phone=" + phone +
                '}';
    }
}
