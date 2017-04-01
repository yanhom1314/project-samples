package com.example.jpa;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "t_role")
public class Role extends BaseEntity {
    @Column(name = "role_name", nullable = false, unique = true)
    public String roleName;
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "roles")
    //@ElementCollection(targetClass = classOf[LoginUser])
    public List<LoginUser> users;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<LoginUser> getUsers() {
        return users;
    }

    public void setUsers(List<LoginUser> users) {
        this.users = users;
    }
}
