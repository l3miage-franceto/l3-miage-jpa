package fr.uga.im2ag.l3.miage.db.model;

import javax.persistence.*;
import java.util.Objects;

// TODO ajouter une named query pour une des requêtes à faire dans le repository
@NamedQueries({
        @NamedQuery(name = "Grade.findHighestGrades", query = "select g from Grade g where g.value > :limit"),
        @NamedQuery(name = "Grade.findHighestGradeByStudent", query = "select g from Grade g where g.subject = :subject and g.value > :limit"),
        @NamedQuery(name = "Grade.findById", query = "select g from Grade g where g.id = :id"),
        @NamedQuery(name = "Grade.getAll", query = "select g from Grade g")
})
@Entity
@Table(name = "Grade")
public class Grade {

    @Id
    @GeneratedValue
    @Column(name = "g_id")
    private Long id;

    @ManyToOne
    private Subject subject;

    @Column(name = "g_value", updatable = false)
    private Float value;

    @Column(name = "g_weight")
    private Float weight;

    public Long getId() {
        return id;
    }

    public Subject getSubject() {
        return subject;
    }

    public Grade setSubject(Subject subject) {
        this.subject = subject;
        return this;
    }

    public Float getValue() {
        return value;
    }

    public Grade setValue(Float value) {
        this.value = value;
        return this;
    }

    public Float getWeight() {
        return weight;
    }

    public Grade setWeight(Float weight) {
        this.weight = weight;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Grade grade = (Grade) o;
        return Objects.equals(id, grade.id)
                && Objects.equals(subject, grade.subject)
                && Objects.equals(value, grade.value)
                && Objects.equals(weight, grade.weight);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, subject, value, weight);
    }

    @Override
    public String toString(){
        return String.format("Grade\nId : %d\nSubject ID : %d, Name : %s\nValue : %f\nWeight : %f\n",
                getId(),
                getSubject().getId(), getSubject().getName(),
                getValue(),
                getWeight());
    }

}
