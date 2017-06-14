package demo.bean;

import demo.validation.LyfNotEmpty;
import demo.validation.Matches;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Matches(field = "password", verifyField = "confirmPassword", message = "{constraint.confirmNewPassword.not.match.newPassword}")
public class Person {
    private Long id = -1L;
    @NotNull
    @Size(min = 2, max = 30)
    private String name;
    @NotNull
    @Min(18)
    private Integer age;
    @Email
    private String email;
    @LyfNotEmpty
    private String address;

    @NotEmpty
    private String password;

    private String confirmPassword;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
