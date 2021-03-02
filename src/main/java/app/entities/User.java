package app.entities;

import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tblUsers")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    @NotBlank(message = "Ім'я обов'язкове")
    private String name;

    @Column(nullable = false)
    @NotBlank(message = "Email обов'язковий")
    private String email;

    @Column(nullable=false)
    @NotBlank(message = "Пароль є обовзяковий")
    private String password;

    @Column()
    @ColumnDefault("0")
    @NotBlank(message = "Баланс обов'язковий")
    private Double balance;

    @ManyToMany(cascade=CascadeType.MERGE)
    @JoinTable(
            name="tblUserRoles",
            joinColumns={@JoinColumn(name="userId", referencedColumnName="id")},
            inverseJoinColumns={@JoinColumn(name="roleId", referencedColumnName="id")})
    private List<Role> roles;

    public User(){
        roles = new ArrayList<Role>();
    }

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

    public Double getBalance(){
        return balance;
    }
    public void setBalance(Double balance){
        if(balance > 0)
            this.balance = balance;
    }

    public List<Role> getRoles(){
        return roles;
    }
    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}