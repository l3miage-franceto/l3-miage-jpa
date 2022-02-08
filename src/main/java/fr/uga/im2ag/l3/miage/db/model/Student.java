package fr.uga.im2ag.l3.miage.db.model;

import javax.persistence.*;
import java.util.List;

// TODO ajouter une named query pour une des requêtes à faire dans le repository
@Entity
@DiscriminatorValue(PersonType.Values.STUDENT)
@NamedQueries({
        @NamedQuery(name = "Student.findById", query = "select s from Student s where s.id = :id"),
        @NamedQuery(name = "Student.findStudentHavingGradeAverageAbove", query = "select s from Student s join s.grades g group by s.id having avg(g.value) > :minAverage"),
        @NamedQuery(name = "Student.getAll", query = "select s from Student s")
})
public class Student extends Person {

    @ManyToOne
    private GraduationClass belongTo;

    @OneToMany
    private List<Grade> grades;

    public GraduationClass getBelongTo() {
        return belongTo;
    }

    public Student setBelongTo(GraduationClass belongTo) {
        this.belongTo = belongTo;
        return this;
    }

    public List<Grade> getGrades() {
        return grades;
    }

    public Student setGrades(List<Grade> grades) {
        this.grades = grades;
        return this;
    }
}
