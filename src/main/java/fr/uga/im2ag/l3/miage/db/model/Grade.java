package fr.uga.im2ag.l3.miage.db.model;

import javax.persistence.*;

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
    private Long id;

    @ManyToOne
    private Subject subject;

    @Column(name = "grade_value")
    private Float value;

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
}
