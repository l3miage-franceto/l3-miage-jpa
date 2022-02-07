package fr.uga.im2ag.l3.miage.db.model;

import javax.persistence.*;
import java.util.Date;

// TODO ajouter une named query pour une des requêtes à faire dans le repository

@Entity
@Table(name = "Subject")
public class Subject {

    @Id
    @GeneratedValue
    @Column(name = "subject_id")
    private Long id;

    @Column(name = "subject_name")
    private String name;

    @Column(name = "subject_points")
    private Integer points;

    @Column(name = "subject_hours")
    private Float hours;

    @Column(name = "subject_start")
    private Date start;

    @Column(name = "subject_end")
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
}
