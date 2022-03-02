package fr.uga.im2ag.l3.miage.db.repository;

import fr.uga.im2ag.l3.miage.db.repository.api.TeacherRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class TeacherTest extends Base {

    TeacherRepository teacherRepository;

    @BeforeEach
    void before() {
        teacherRepository = daoFactory.newTeacherRepository(entityManager);
    }

    @AfterEach
    void after() {
        if (entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().rollback();
        }
    }

    @Test
    void shouldSaveTeacher()  {
        // TODO
        final var subject1 = Fixtures.createSubject();
        final var class1 = Fixtures.createClass();
        final var teacher1 = Fixtures.createTeacher(subject1, class1);

        final var subject2 = Fixtures.createSubject();
        final var class2 = Fixtures.createClass();
        final var teacher2 = Fixtures.createTeacher(subject2, class2);

        entityManager.getTransaction().begin();

        entityManager.persist(subject1);
        entityManager.persist(class1);
        teacherRepository.save(teacher1);

        entityManager.persist(subject2);
        entityManager.persist(class2);
        teacherRepository.save(teacher2);

        entityManager.getTransaction().commit();

        entityManager.detach(teacher1);
        entityManager.detach(teacher2);

        var pTeacher1 = teacherRepository.findById(teacher1.getId());
        assertThat(pTeacher1).isNotNull().isNotSameAs(teacher1).isEqualTo(teacher1);
        var pTeacher2 = teacherRepository.findById(teacher2.getId());
        assertThat(pTeacher2).isNotNull().isNotSameAs(teacher2).isEqualTo(teacher2);

    }

    @Test
    void shouldFindHeadingGraduationClassByYearAndName() {
        // TODO
        final var subject1 = Fixtures.createSubject();
        final var class1 = Fixtures.createClass();
        class1.setYear(1978);
        class1.setName("Prolog");
        final var teacher1 = Fixtures.createTeacher(subject1, class1);

        final var subject2 = Fixtures.createSubject();
        final var class2 = Fixtures.createClass();
        class2.setName("Angular");
        class2.setYear(2019);
        final var teacher2 = Fixtures.createTeacher(subject2, class2);

        entityManager.getTransaction().begin();
        entityManager.persist(subject1);
        entityManager.persist(class1);
        teacherRepository.save(teacher1);
        entityManager.persist(subject2);
        entityManager.persist(class2);
        teacherRepository.save(teacher2);
        entityManager.getTransaction().commit();

        entityManager.detach(subject1);
        entityManager.detach(subject2);
        entityManager.detach(class1);
        entityManager.detach(class2);
        entityManager.detach(teacher1);
        entityManager.detach(teacher2);

        var pTeacher2 = teacherRepository.findHeadingGraduationClassByYearAndName(2019, "Angular");
        assertThat(pTeacher2).isNotNull().isNotSameAs(teacher2).isEqualTo(teacher2);
        var pTeacher1 = teacherRepository.findHeadingGraduationClassByYearAndName(1978, "Prolog");
        assertThat(pTeacher1).isNotNull().isNotSameAs(teacher1).isEqualTo(teacher1);
    }

}
