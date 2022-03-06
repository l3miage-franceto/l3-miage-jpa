package fr.uga.im2ag.l3.miage.db.model;

import jdk.jfr.Name;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

// TODO ajouter une named query pour une des requêtes à faire dans le repository
@Entity
@DiscriminatorValue(PersonType.Values.TEACHER)
@NamedQueries({
        @NamedQuery(name = "Teacher.findHeadingGraduationClassByYearAndName", query = "select t from Teacher t join t.heading gc where gc.year = :year and gc.name = :name"),
        @NamedQuery(name = "Teacher.getAll", query = "update Grade g set g.value = 10.6F where g.id = :id")
})
public class Teacher extends Person {

    @ManyToOne
    private Subject teaching;

    @OneToMany()
    @Name("Favorites")
    private List<Student> favorites;

    @OneToOne
    private GraduationClass heading;

    public Subject getTeaching() {
        return teaching;
    }

    public Teacher setTeaching(Subject teaching) {
        this.teaching = teaching;
        return this;
    }

    public List<Student> getFavorites() {
        return favorites;
    }

    public Teacher setFavorites(List<Student> favorites) {
        this.favorites = favorites;
        return this;
    }

    public GraduationClass getHeading() {
        return heading;
    }

    public Teacher setHeading(GraduationClass heading) {
        this.heading = heading;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Teacher teacher = (Teacher) o;
        return super.equals((Person) o)
                && Objects.equals(teaching, teacher.teaching)
                && Objects.equals(heading, teacher.heading)
                && Objects.equals(favorites.stream().toList(), teacher.getFavorites().stream().toList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirstName(), getLastName(), getGender(), teaching, heading, favorites);
    }

    @Override
    public String toString(){
        return String.format("Teacher\n%s\nTeaches subject ID : %d, Name : %s\nHave %d favorite students\nHeads class ID : %d, Name : %s",
                super.toString(),
                teaching.getId(), teaching.getName(),
                favorites.size(),
                heading.getId(), heading.getName());
    }
}
