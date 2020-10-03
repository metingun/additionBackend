package com.website.backend.model;

import javax.persistence.*;

@Entity
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    private String userName;

    @Column
    private String password;

    @Column
    private String session="eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMDAxIiwic2NvcGVzIjoiQ1NBX1RSIiwiaWF0IjoxNTk4NDI2NDM1LCJleHAiOjE1OTg0NDQ0MzV9.NIm0x8AB4ZNJLlP4s_PNkcyKm3PaJjEGlNP0Yl7MJaQ";

    @Column
    private int power; // admin(100) ---- garson(101) ----- aşçı(102)

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

}
