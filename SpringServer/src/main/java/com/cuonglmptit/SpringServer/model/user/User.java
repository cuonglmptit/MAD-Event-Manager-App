package com.cuonglmptit.SpringServer.model.user;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "tblUser")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "username", nullable = false, length = 255)
    private String username;

    @Column(name = "password", nullable = false, length = 255)
    private String password;  // **Please note: Store passwords securely, not as plain text**

    @Column(name = "name", length = 255)
    private String name;

    @Column(name = "dob")
    private Date dob;

    @Column(name = "phonenumber", length = 10)
    private String phoneNumber;


    // Constructors, getters, and setters


    public User() {
    }

    public User(int id, String username, String password, String name, Date dob, String phoneNumber) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.dob = dob;
        this.phoneNumber = phoneNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", dob=" + dob +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}