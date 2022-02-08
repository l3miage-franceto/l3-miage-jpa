package fr.uga.im2ag.l3.miage.db.model;

import jdk.jfr.Name;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

// TODO ajouter une named query pour une des requêtes à faire dans le repository
@Entity
@NamedQueries({
        @NamedQuery(name = "GraduationClass.findByYearAndName", query = "select gc from GraduationClass gc where gc.name = :name and gc.year = :year "),
        @NamedQuery(name = "GraduationClass.findById", query = "select gc from GraduationClass gc where gc.id = :id"),
        @NamedQuery(name = "GraduationClass.getAll", query = "select gc from GraduationClass gc")
})
public class GraduationClass {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Column(name = "gc_year")
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
