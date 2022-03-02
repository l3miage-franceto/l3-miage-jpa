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
@Table(uniqueConstraints =
        { @UniqueConstraint(name = "uniqueClassNameForYear", columnNames = {"gc_name", "gc_year"}) }
)
public class GraduationClass {

    @Id
    @GeneratedValue
    @Column(name = "gc_id")
    private Long id;

    @Column(name = "gc_name", nullable = false)
    private String name;

    @Column(name = "gc_year", nullable = false)
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
        GraduationClass gc = (GraduationClass) o;
        return Objects.equals(id, gc.id)
                && Objects.equals(name, gc.name)
                && Objects.equals(year, gc.year)
                && Objects.equals(getStudents().stream().toList(), gc.getStudents().stream().toList());
                //on passe par stream().toList() car sinon students est de type PersistentBag, qui implémente l'interface List, et sa méthode equals ne fonctionnait pas correctement
                //ainsi on a bien students qui est de type List
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, year, students);
    }

    @Override
    public String toString(){
        return String.format("Graduation class\nId : %d\nName : %s\nYear : %d\nNumber of students : %d",
                id,
                name,
                year,
                students.size());
    }
}
