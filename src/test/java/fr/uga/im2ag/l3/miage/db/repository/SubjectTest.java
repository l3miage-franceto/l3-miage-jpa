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
        assertThat(pSubject1).isNotNull().isNotSameAs(subject1).isEqualTo(subject1);

        // test subject 2
        var pSubject2 = subjectRepository.findById(subject2.getId());
        assertThat(pSubject2).isNotNull().isNotSameAs(subject2).isEqualTo(subject2);
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
        assertThat(pTeachersSubject1).isNotEmpty();
        var pTeachersSubject1Rep = new ArrayList<>(Arrays.asList(teacher1));
        assertThat(pTeachersSubject1).isEqualTo(pTeachersSubject1Rep);

        var pTeachersSubject2 = (ArrayList<Teacher>) subjectRepository.findTeachers(subject2.getId());
        assertThat(pTeachersSubject2).isNotEmpty();
        var pTeachersSubject2Rep = new ArrayList<>(Arrays.asList(teacher2, teacher3));
        assertThat(pTeachersSubject2).isEqualTo(pTeachersSubject2Rep);

    }
}
