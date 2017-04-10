package demo.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "oauth2_user")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; //编号
    @Column(name = "username", unique = true, nullable = false, length = 100)
    private String username; //用户名
    @Column(name = "password", nullable = false, length = 128)
    private String password; //密码

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
}
