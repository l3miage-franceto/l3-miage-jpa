package fr.uga.im2ag.l3.miage.db.model;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

// TODO ajouter une named query pour une des requêtes à faire dans le repository
@Entity
@DiscriminatorValue(PersonType.Values.STUDENT)
@NamedQueries({
        @NamedQuery(name = "Student.findById", query = "select s from Student s where s.id = :id"),
        @NamedQuery(name = "Student.findStudentHavingGradeAverageAbove", query = "select s from Student s where s.id in (select s2.id from Student s2 join s2.grades g group by s2.id having (sum(g.value * g.weight)/sum(g.weight)) > :minAverage)"),  //(sum(g.value * g.weight)/sum(g.weight))
        @NamedQuery(name = "Student.getAll", query = "select s from Student s")
})
public class Student extends Person {

    @ManyToOne
    private GraduationClass belongTo;

    @OneToMany(cascade = CascadeType.ALL)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return super.equals((Person) o)
                && Objects.equals(getBelongTo(), student.getBelongTo())
                && Objects.equals(getGrades().stream().toList(), student.getGrades().stream().toList());
                //on passe par stream().toList() car sinon grades est de type PersistentBag, qui implémente l'interface List, et sa méthode equals ne fonctionnait pas correctement
                //ainsi on a bien grades qui est de type List

    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirstName(), getLastName(), getGender(), belongTo, grades);
    }

    @Override
    public String toString(){
        return String.format("Student\n%s\nBelongs to class ID : %d, Name : %s\n",
                super.toString(),
                getBelongTo().getId(), getBelongTo().getName());
    }
}
