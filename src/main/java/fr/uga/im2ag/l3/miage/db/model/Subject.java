package fr.uga.im2ag.l3.miage.db.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

// TODO ajouter une named query pour une des requêtes à faire dans le repository

@Entity
@Table(name = "Subject")
@NamedQueries({
        @NamedQuery(name = "Subject.getAll", query = "select s from Subject s"),
        @NamedQuery(name = "Subject.findTeachers", query = "select t from Teacher t join t.teaching s where s.id = :id")
})
public class Subject {

    @Id
    @GeneratedValue
    @Column(name = "sub_id")
    private Long id;

    @Column(name = "sub_name", unique = true, nullable = false)
    private String name;

    @Column(name = "sub_points")
    private Integer points;

    @Column(name = "sub_hours", nullable = false)
    private Float hours;

    @Column(name = "sub_start", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date start;

    @Column(name = "sub_end", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date end;

    public Long getId() {
        return id;
    }

    public Subject setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Subject setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getPoints() {
        return points;
    }

    public Subject setPoints(Integer points) {
        this.points = points;
        return this;
    }

    public Float getHours() {
        return hours;
    }

    public Subject setHours(Float hours) {
        this.hours = hours;
        return this;
    }

    public Date getStart() {
        return start;
    }

    public Subject setStart(Date start) {
        this.start = start;
        return this;
    }

    public Date getEnd() {
        return end;
    }

    public Subject setEnd(Date end) {
        this.end = end;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subject subject = (Subject) o;
        return Objects.equals(id, subject.id)
                && Objects.equals(name, subject.name)
                && Objects.equals(points, subject.points)
                && Objects.equals(hours, subject.hours)
                && Objects.equals(start.getTime(), subject.getStart().getTime())
                && Objects.equals(end.getTime(), subject.getEnd().getTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, points, hours, start, end);
    }

    @Override
    public String toString(){
        return String.format("Subject\nID : %d\nName : %s\nPoints : %d\nHours : %f\nStart : %s\nEnd : %s",
                id,
                name,
                points,
                hours,
                start.toString(),
                end.toString());
    }
}
