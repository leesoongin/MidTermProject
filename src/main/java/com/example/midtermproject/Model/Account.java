package com.example.midtermproject.Model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
public class Account {
    private String id;
    private String password;
    private String name;
    private String phone;
    private String address;

    public void setId(String id){ this.id = id; }
    public void setPassword(String password){ this.password = password; }
    public void setName(String name){ this.name = name; }
    public void setPhone(String phone){ this.phone = phone; }
    public void setAddress(String address) { this.address = address; }
    public String getId(){ return this.id; }
    public String getPassword(){ return this.password; }
    public String getName(){ return this.name; }
    public String getPhone(){ return this.phone; }
    public String getAddress(){ return this.address; }
}
