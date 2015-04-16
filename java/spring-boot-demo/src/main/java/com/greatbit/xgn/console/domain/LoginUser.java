package com.greatbit.xgn.console.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "gn_user")
public class LoginUser implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(length = 50, unique = true)
    private String username;
    @Column(length = 50)
    private String password;
    @Column
    private Timestamp loginDate = new Timestamp(System.currentTimeMillis());
    @Column(length = 20)
    private String loginIp;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "gn_user_role", joinColumns = {@JoinColumn(name = "u_id", referencedColumnName = "id")}, inverseJoinColumns = {@JoinColumn(name = "t_id", referencedColumnName = "id")})
    private Set<Role> roles = new HashSet<Role>();

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

    public Timestamp getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Timestamp loginDate) {
        this.loginDate = loginDate;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
