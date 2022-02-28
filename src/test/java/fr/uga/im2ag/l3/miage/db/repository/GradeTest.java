package fr.uga.im2ag.l3.miage.db.repository;

import fr.uga.im2ag.l3.miage.db.model.Grade;
import fr.uga.im2ag.l3.miage.db.repository.api.GradeRepository;
import fr.uga.im2ag.l3.miage.db.repository.api.SubjectRepository;
import lombok.extern.java.Log;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Log
class GradeTest extends Base {

    GradeRepository gradeRepository;

    @BeforeEach
    void before() {
        gradeRepository = daoFactory.newGradeRepository(entityManager);
    }

    @AfterEach
    void after() {
        if (entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().rollback();
        }
    }

    @Test
    void shouldSaveGrade() {
        // TODO
        final var grade = Fixtures.createGrade(Fixtures.createSubject());

        entityManager.getTransaction().begin();
        entityManager.persist(grade.getSubject());
        gradeRepository.save(grade);
        entityManager.getTransaction().commit();

        entityManager.detach(grade);

        var pGrade = gradeRepository.findById(grade.getId());
        assertThat(pGrade).isNotNull().isNotSameAs(grade).isEqualTo(grade);
    }

    @Test
    void shouldFailUpgradeGrade() {
        // TODO, ici tester que la mise à jour n'a pas eu lieu.
        final var subject = Fixtures.createSubject();
        final var grade = Fixtures.createGrade(subject);

        entityManager.getTransaction().begin();
        entityManager.persist(subject);
        gradeRepository.save(grade);
        entityManager.getTransaction().commit();
        entityManager.detach(grade);

        var pGrade = gradeRepository.findById(grade.getId());
        assertThat(pGrade).isNotNull().isNotSameAs(grade);
        pGrade.setValue((float)12.673);
        assertThat(pGrade).isNotEqualTo(grade);
    }

    @Test
    void shouldFindHighestGrades() {
        // TODO
        final var subject1 = Fixtures.createSubject();
        final var subject2 = Fixtures.createSubject();
        final var subject3 = Fixtures.createSubject();

        final var grade1 = Fixtures.createGrade(subject1);
        final var grade2 = Fixtures.createGrade(subject2);
        final var grade3 = Fixtures.createGrade(subject2);
        final var grade4 = Fixtures.createGrade(subject3);

        grade1.setValue((float) 17.0);
        grade2.setValue((float) 13.6);
        grade3.setValue((float) 11.3);
        grade4.setValue((float) 14.1);

        entityManager.getTransaction().begin();
        entityManager.persist(subject1);
        entityManager.persist(subject2);
        entityManager.persist(subject3);

        gradeRepository.save(grade1);
        gradeRepository.save(grade2);
        gradeRepository.save(grade3);
        gradeRepository.save(grade4);

        entityManager.getTransaction().commit();

        entityManager.detach(subject1);
        entityManager.detach(subject2);
        entityManager.detach(subject3);
        entityManager.detach(grade1);
        entityManager.detach(grade2);
        entityManager.detach(grade3);
        entityManager.detach(grade4);

        var higherGrades = (ArrayList<Grade>) gradeRepository.findHighestGrades((float)13.9);
        var higherGradesR = new ArrayList<>(Arrays.asList(grade1, grade4));
        assertThat(higherGrades).isNotNull().isEqualTo(higherGradesR);
    }

    @Test
    void shouldFindHighestGradesBySubject() {
        // TODO
        final var subject1 = Fixtures.createSubject();
        final var subject2 = Fixtures.createSubject();
        final var subject3 = Fixtures.createSubject();

        final var grade1 = Fixtures.createGrade(subject1);
        final var grade2 = Fixtures.createGrade(subject2);
        final var grade3 = Fixtures.createGrade(subject2);
        final var grade4 = Fixtures.createGrade(subject3);

        grade1.setValue((float) 17.0);
        grade2.setValue((float) 13.6);
        grade3.setValue((float) 11.3);
        grade4.setValue((float) 14.1);

        entityManager.getTransaction().begin();
        entityManager.persist(subject1);
        entityManager.persist(subject2);
        entityManager.persist(subject3);

        gradeRepository.save(grade1);
        gradeRepository.save(grade2);
        gradeRepository.save(grade3);
        gradeRepository.save(grade4);

        entityManager.getTransaction().commit();

        entityManager.detach(grade1);
        entityManager.detach(grade2);
        entityManager.detach(grade3);
        entityManager.detach(grade4);
        entityManager.detach(subject1);
        entityManager.detach(subject2);
        entityManager.detach(subject3);

        var higherGradesSubject = (ArrayList<Grade>) gradeRepository.findHighestGradesBySubject((float) 13.1, subject2);
        var higherGradesSubjectR = new ArrayList<>(Arrays.asList(grade2));
        assertThat(higherGradesSubject).isEqualTo(higherGradesSubjectR);
    }

}
