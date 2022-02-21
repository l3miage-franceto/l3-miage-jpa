package fr.uga.im2ag.l3.miage.db.model;

import jdk.jfr.Name;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// TODO ajouter une named query pour une des requêtes à faire dans le repository
@Entity
@NamedQueries({
        @NamedQuery(name = "GraduationClass.findByYearAndName", query = "select gc from GraduationClass gc where gc.name = :name and gc.year = :year "),
        @NamedQuery(name = "GraduationClass.getAll", query = "select gc from GraduationClass gc")
})
public class GraduationClass {

    @Id
    @GeneratedValue
    @Column(name = "gc_id")
    private Long id;

    @Column(name = "gc_name")
    private String name;

    @Column(name = "gc_year")
    private Integer year;

    @OneToMany(mappedBy = "belongTo")
    @Column(name = "gc_students")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GraduationClass that = (GraduationClass) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(year, that.year) && Objects.equals(students, that.students);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, students);
    }
}
