package fr.uga.im2ag.l3.miage.db.repository;

import fr.uga.im2ag.l3.miage.db.model.Grade;
import fr.uga.im2ag.l3.miage.db.model.Subject;
import fr.uga.im2ag.l3.miage.db.repository.api.GradeRepository;
import fr.uga.im2ag.l3.miage.db.repository.api.SubjectRepository;
import lombok.extern.java.Log;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


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
        grade.setValue(13.6F);

        entityManager.getTransaction().begin();
        entityManager.persist(subject);
        gradeRepository.save(grade);
        entityManager.getTransaction().commit();

        grade.setValue(9.8F);
        gradeRepository.save(grade);    //équivalent update

        entityManager.detach(grade);

        var pGrade = gradeRepository.findById(grade.getId());
        assertThat(pGrade.getValue()).isNotEqualTo(grade.getValue());

    }

    @Test
    void shouldFindHighestGrades() {
        // TODO
        final var grade1 = Fixtures.createGrade(Fixtures.createSubject());
        final var grade2 = Fixtures.createGrade(Fixtures.createSubject());
        final var grade3 = Fixtures.createGrade(Fixtures.createSubject());
        final var grade4 = Fixtures.createGrade(Fixtures.createSubject());

        grade1.setValue(17.0F);
        grade2.setValue(13.6F);
        grade3.setValue(11.3F);
        grade4.setValue(14.1F);

        entityManager.getTransaction().begin();

        entityManager.persist(grade1.getSubject());
        entityManager.persist(grade2.getSubject());
        entityManager.persist(grade3.getSubject());
        entityManager.persist(grade4.getSubject());

        gradeRepository.save(grade1);
        gradeRepository.save(grade2);
        gradeRepository.save(grade3);
        gradeRepository.save(grade4);

        entityManager.getTransaction().commit();

        entityManager.detach(grade1);
        entityManager.detach(grade2);
        entityManager.detach(grade3);
        entityManager.detach(grade4);

        var higherGrades = (ArrayList<Grade>) gradeRepository.findHighestGrades(13.9F);
        assertThat(higherGrades).isNotEmpty();
        var higherGradesR = new ArrayList<>(Arrays.asList(grade1, grade4));
        assertThat(higherGrades).isEqualTo(higherGradesR);
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
        final var grade5 = Fixtures.createGrade(subject2);

        grade1.setValue(17.0F);
        grade2.setValue(13.6F);
        grade3.setValue(11.3F);
        grade4.setValue(14.1F);
        grade5.setValue(18.98F);

        entityManager.getTransaction().begin();
        entityManager.persist(subject1);
        entityManager.persist(subject2);
        entityManager.persist(subject3);

        gradeRepository.save(grade1);
        gradeRepository.save(grade2);
        gradeRepository.save(grade3);
        gradeRepository.save(grade4);
        gradeRepository.save(grade5);

        entityManager.getTransaction().commit();

        var higherGradesSubject = (ArrayList<Grade>) gradeRepository.findHighestGradesBySubject(13.1F, subject2);
        assertThat(higherGradesSubject).isNotEmpty();
        var higherGradesSubjectR = new ArrayList<>(Arrays.asList(grade2, grade5));
        assertThat(higherGradesSubject).isEqualTo(higherGradesSubjectR);
    }

}
