package demo.db;

import io.sinq.annotation.Column;
import io.sinq.annotation.Table;

@Table(name = "t_person")
public class Person {
    private long id;
    @Column(name = "user_name")
    private String name;
    private String address;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void sayHi(String name) {
        System.out.println("Hello " + name + " by " + this.name + "!");
    }
}
