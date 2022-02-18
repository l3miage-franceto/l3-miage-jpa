package fr.uga.im2ag.l3.miage.db.repository;

import fr.uga.im2ag.l3.miage.db.model.Teacher;
import fr.uga.im2ag.l3.miage.db.repository.api.SubjectRepository;
import lombok.extern.java.Log;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

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
        final var subject3 = Fixtures.createSubject();

        entityManager.getTransaction().begin();
        subjectRepository.save(subject1);
        subjectRepository.save(subject2);
        subjectRepository.save(subject3);
        entityManager.getTransaction().commit();
        entityManager.detach(subject1);
        entityManager.detach(subject2);
        entityManager.detach(subject3);

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

        // test subject 3
        var pSubject3 = subjectRepository.findById(subject3.getId());
        assertThat(pSubject3).isNotNull().isNotSameAs(subject3);
        assertThat(pSubject3.getName()).isEqualTo(subject3.getName());
        assertThat(pSubject3.getHours()).isEqualTo(subject3.getHours());
        assertThat(pSubject3.getPoints()).isEqualTo(subject3.getPoints());
        assertThat(pSubject3.getClass()).isEqualTo(subject3.getClass());
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

        var pTeachersSubject1 = (ArrayList<Teacher>) subjectRepository.findTeachers(subject1.getId());
        var pTeachersSubject1Rep = new ArrayList<>(Arrays.asList(teacher1));
        assertThat(pTeachersSubject1).isNotNull().isEqualTo(pTeachersSubject1Rep);

        var pTeachersSubject2 = (ArrayList<Teacher>) subjectRepository.findTeachers(subject2.getId());
        var pTeachersSubject2Rep = new ArrayList<>(Arrays.asList(teacher2, teacher3));
        assertThat(pTeachersSubject2).isNotNull().isEqualTo(pTeachersSubject2Rep);
    }

}
