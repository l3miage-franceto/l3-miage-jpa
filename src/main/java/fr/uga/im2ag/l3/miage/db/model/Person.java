package fr.uga.im2ag.l3.miage.db.model;

import javax.persistence.*;
import java.util.Date;

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Entity
@DiscriminatorColumn(name = "type")
public abstract class Person {

    @Id
    @GeneratedValue
    @Column(name = "p_id")
    private Long id;

    @Column(name = "p_gender")
    private Gender gender;

    @Column(name = "p_firstname")
    private String firstName;

    @Column(name = "p_lastname")
    private String lastName;

    @Column(name = "p_birth")
    private Date birth;

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public Person setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public Person setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public Person setId(Long id) {
        this.id = id;
        return this;
    }

    public Date getBirth() {
        return birth;
    }

    public Person setBirth(Date birth) {
        this.birth = birth;
        return this;
    }

    public Gender getGender() {
        return gender;
    }

    public Person setGender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public enum Gender {
        FEMALE, MALE, FLUID
    }

}
