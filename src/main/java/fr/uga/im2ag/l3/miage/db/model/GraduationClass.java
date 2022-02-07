package fr.uga.im2ag.l3.miage.db.model;

import jdk.jfr.Name;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

// TODO ajouter une named query pour une des requêtes à faire dans le repository
@Entity
public class GraduationClass {

    @Id
    @GeneratedValue
    @Column(name = "graduation_class_id")
    private Long id;

    @Column(name = "graduation_class_name")
    private String name;

    @Column(name = "graduation_class_year")
    private Integer year;

    @OneToMany(mappedBy = "belongTo")
    private List<Student> students;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public GraduationClass setId(Long id) {
        this.id = id;
        return this;
    }

    public GraduationClass setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getYear() {
        return year;
    }

    public GraduationClass setYear(Integer year) {
        this.year = year;
        return this;
    }

    public List<Student> getStudents() {
        return students;
    }

    public GraduationClass setStudents(List<Student> students) {
        this.students = students;
        return this;
    }

    public void addStudent(Student student) {
        if (students == null) {
            students = new ArrayList<>();
        }
        students.add(student);
    }
}
