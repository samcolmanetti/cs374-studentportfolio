package com.cas.portfolio.web.model;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
 
public class WebCustomer {
    
    @NotEmpty
    @Email
    private String email;
    
    @Size(min=3,max=15)
    private String password;
    
    @Size(min=3,max=15)
    private String confPassword;
    
    private int age;
 
    public String getEmail() {
        return email;
    }
 
    public void setEmail(String email) {
        this.email = email;
    }
 
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
 
    public String getConfPassword() {
        return confPassword;
    }
 
    public void setConfPassword(String confPassword) {
        this.confPassword = confPassword;
    }
 
    public int getAge() {
        return age;
    }
 
    public void setAge(int age) {
        this.age = age;
    }
}
