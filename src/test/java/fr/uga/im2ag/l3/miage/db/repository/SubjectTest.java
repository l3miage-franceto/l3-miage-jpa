package fr.uga.im2ag.l3.miage.db.repository;

import fr.uga.im2ag.l3.miage.db.model.Person;
import fr.uga.im2ag.l3.miage.db.model.Student;
import fr.uga.im2ag.l3.miage.db.model.Subject;
import fr.uga.im2ag.l3.miage.db.model.Teacher;
import fr.uga.im2ag.l3.miage.db.repository.api.SubjectRepository;
import lombok.extern.java.Log;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Log
class SubjectTest extends Base {

    SubjectRepository subjectRepository;

    @BeforeEach
    void before() {
        subjectRepository = daoFactory.newSubjectRepository(entityManager);
    }

    @AfterEach
    void after() {
        if (entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().rollback();
        }
    }

    @Test
    void shouldSaveSubject() {

        final var subject1 = Fixtures.createSubject();
        final var subject2 = Fixtures.createSubject();

        entityManager.getTransaction().begin();
        subjectRepository.save(subject1);
        subjectRepository.save(subject2);
        entityManager.getTransaction().commit();

        entityManager.detach(subject1);
        entityManager.detach(subject2);

        // test subject 1
        var pSubject1 = subjectRepository.findById(subject1.getId());
        assertThat(pSubject1).isNotNull().isNotSameAs(subject1);
        assertThat(pSubject1.getName()).isEqualTo(subject1.getName());
        assertThat(pSubject1.getHours()).isEqualTo(subject1.getHours());
        assertThat(pSubject1.getPoints()).isEqualTo(subject1.getPoints());
        assertThat(pSubject1.getClass()).isEqualTo(subject1.getClass());

        // test subject 2
        var pSubject2 = subjectRepository.findById(subject2.getId());
        assertThat(pSubject2).isNotNull().isNotSameAs(subject2);
        assertThat(pSubject2.getName()).isEqualTo(subject2.getName());
        assertThat(pSubject2.getHours()).isEqualTo(subject2.getHours());
        assertThat(pSubject2.getPoints()).isEqualTo(subject2.getPoints());
        assertThat(pSubject2.getClass()).isEqualTo(subject2.getClass());
    }

    @Test
    void shouldFindTeachersForSubject() {
        // TODO
        final var subject1 = Fixtures.createSubject();
        final var subject2 = Fixtures.createSubject();

        final var student1 = Fixtures.createStudent(Fixtures.createClass());
        final var student2 = Fixtures.createStudent(Fixtures.createClass());
        final var student3 = Fixtures.createStudent(Fixtures.createClass());

        final var teacher1 = Fixtures.createTeacher(subject1, null, student1);
        final var teacher2 = Fixtures.createTeacher(subject2, null, student2);
        final var teacher3 = Fixtures.createTeacher(subject2, null, student3);

        final Collection<Teacher> teachersSubject1 = new ArrayList<>();
        teachersSubject1.add(teacher1);

        final Collection<Teacher> teachersSubject2 = new ArrayList<>();
        teachersSubject2.add(teacher2);
        teachersSubject2.add(teacher3);

        entityManager.getTransaction().begin();

        entityManager.persist(subject1);
        entityManager.persist(subject2);

        entityManager.persist(student1);
        entityManager.persist(student2);
        entityManager.persist(student3);

        entityManager.persist(student1.getBelongTo());
        entityManager.persist(student2.getBelongTo());
        entityManager.persist(student3.getBelongTo());

        entityManager.persist(teacher1);
        entityManager.persist(teacher2);
        entityManager.persist(teacher3);

        entityManager.getTransaction().commit();

        entityManager.detach(subject1);
        entityManager.detach(subject2);
        entityManager.detach(student1);
        entityManager.detach(student2);
        entityManager.detach(student3);
        entityManager.detach(teacher1);
        entityManager.detach(teacher2);
        entityManager.detach(teacher3);

        var pTeachersSubject1 = (ArrayList<Teacher>) subjectRepository.findTeachers(subject1.getId());
        log.info(teacher1.toString());
        log.info(pTeachersSubject1.get(0).toString());
        assertThat(pTeachersSubject1).isNotNull();
        var pTeachersSubject1Rep = new ArrayList<>(Arrays.asList(teacher1));
        assertThat(pTeachersSubject1.size()).isEqualTo(pTeachersSubject1Rep.size());
        log.info("taille " + pTeachersSubject1Rep.get(0).getFavorites().size() + ", " + pTeachersSubject1.get(0).getFavorites().size());

        ArrayList<Subject> monArray = new ArrayList<>(Arrays.asList(subject1));
        ArrayList<Subject> monArray2 = new ArrayList<>(Arrays.asList(subject1));
        log.info("test equals " + pTeachersSubject1.get(0).getFavorites().equals(pTeachersSubject1Rep.get(0).getFavorites()));

        Student s1 = Fixtures.createStudent(student1.getBelongTo());
        s1.setFirstName("tata");
        s1.setGender(Person.Gender.FEMALE);
        s1.setBirth(null);
        s1.setGrades(null);
        s1.setLastName("toto");
        s1.setId((long)100);
        List<Student> myList = List.of(s1);

        Student s2 = Fixtures.createStudent(student1.getBelongTo());
        s2.setFirstName("tata");
        s2.setGender(Person.Gender.FEMALE);
        s2.setBirth(null);
        s2.setGrades(null);
        s2.setLastName("toto");
        s2.setId((long)100);
        List<Student> myList2 = List.of(s2);

        log.info("test equ lis student " + myList.equals(myList2));
        subject2.setId(subject1.getId());
        subject2.setHours(subject1.getHours());
        subject2.setName(subject1.getName());
        subject2.setPoints(subject1.getPoints());
        log.info("test equ subject " + subject1.equals(subject2));
        log.info("test equ lis student " + myList.equals(myList2));

        log.info("favorites teacher1 id: " + pTeachersSubject1.get(0).getFavorites().get(0).getId());
        log.info("favorites teacher1Rep id: " + pTeachersSubject1Rep.get(0).getFavorites().get(0).getId());
        log.info("favorites teacher1 birth: " + pTeachersSubject1.get(0).getFavorites().get(0).getBirth());
        log.info("favorites teacher1Rep birth: " + pTeachersSubject1Rep.get(0).getFavorites().get(0).getBirth());
        log.info("favorites teacher1 fn: " + pTeachersSubject1.get(0).getFavorites().get(0).getFirstName());
        log.info("favorites teacher1Rep fn: " + pTeachersSubject1Rep.get(0).getFavorites().get(0).getFirstName());
        log.info("favorites teacher1 gc: " + pTeachersSubject1.get(0).getFavorites().get(0).getBelongTo());
        log.info("favorites teacher1Rep gc: " + pTeachersSubject1Rep.get(0).getFavorites().get(0).getBelongTo());
        log.info("favorites teacher1 fav: " + pTeachersSubject1.get(0).getFavorites());
        log.info("favorites teacher1Rep fav: " + pTeachersSubject1Rep.get(0).getFavorites());
        log.info("res comparaison " + pTeachersSubject1Rep.equals(pTeachersSubject1));
        log.info("\n\nteacher 1 " + pTeachersSubject1.get(0).toString() + "\n\nteacher rep " + pTeachersSubject1Rep.get(0).toString());
        /*Teacher[] arrayT1 = (Teacher[]) pTeachersSubject1.toArray();
        Teacher[] arrayT2 = (Teacher[]) pTeachersSubject1Rep.toArray();
        assertArrayEquals(arrayT1, arrayT2);

        var pTeachersSubject2 = (ArrayList<Teacher>) subjectRepository.findTeachers(subject2.getId());
        assertThat(pTeachersSubject2).isNotNull();
        var pTeachersSubject2Rep = new ArrayList<>(Arrays.asList(teacher2, teacher3));
        assertArrayEquals(pTeachersSubject2.toArray(), pTeachersSubject2Rep.toArray());

         */
    }
}
