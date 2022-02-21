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
        var pGradeTemp = gradeRepository.findById(grade.getId());
        log.info(String.format("grade : %s", pGradeTemp.toString()));
        entityManager.getTransaction().commit();
        entityManager.detach(grade);

        var pGrade = gradeRepository.findById(grade.getId());
        assertThat(pGrade).isNotNull().isNotSameAs(grade);
        assertThat(pGrade.getWeight()).isEqualTo(grade.getWeight());
        assertThat(pGrade.getId()).isEqualTo(grade.getId());
        assertThat(pGrade.getValue()).isEqualTo(grade.getValue());
        assertThat(pGrade.getSubject().getId()).isEqualTo(grade.getSubject().getId());
        assertThat(pGrade.getSubject().getName()).isEqualTo(grade.getSubject().getName());
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
        assertThat(pGrade.getValue()).isNotEqualTo(grade.getValue());
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

        var pGrade1 = gradeRepository.findById(grade1.getId());
        assertThat(pGrade1).isNotNull().isNotSameAs(grade1);
        var pGrade2 = gradeRepository.findById(grade2.getId());
        assertThat(pGrade2).isNotNull().isNotSameAs(grade2);
        var pGrade3 = gradeRepository.findById(grade3.getId());
        assertThat(pGrade3).isNotNull().isNotSameAs(grade3);
        var pGrade4 = gradeRepository.findById(grade4.getId());
        assertThat(pGrade4).isNotNull().isNotSameAs(grade4);

        var higherGrades = (ArrayList<Grade>) gradeRepository.findHighestGrades((float)14.0);
        var higherGradesR = new ArrayList<>(Arrays.asList(grade1, grade4));
        assertThat(higherGrades.size()).isEqualTo(higherGradesR.size());
        for(int i = 0; i < higherGrades.size(); i++){
            assertThat(higherGrades.get(i).getId()).isEqualTo(higherGradesR.get(i).getId());
            assertThat(higherGrades.get(i).getValue()).isEqualTo(higherGradesR.get(i).getValue());
            assertThat(higherGrades.get(i).getWeight()).isEqualTo(higherGradesR.get(i).getWeight());
            assertThat(higherGrades.get(i).getClass()).isEqualTo(higherGradesR.get(i).getClass());
            assertThat(higherGrades.get(i).getSubject().getId()).isEqualTo(higherGradesR.get(i).getSubject().getId());
            assertThat(higherGrades.get(i).getSubject().getName()).isEqualTo(higherGradesR.get(i).getSubject().getName());
        }
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

        var pGrade1 = gradeRepository.findById(grade1.getId());
        var pGrade2 = gradeRepository.findById(grade2.getId());
        var pGrade3 = gradeRepository.findById(grade3.getId());
        var pGrade4 = gradeRepository.findById(grade4.getId());

        assertThat(pGrade1).isNotNull().isNotSameAs(grade1);
        assertThat(pGrade2).isNotNull().isNotSameAs(grade2);
        assertThat(pGrade3).isNotNull().isNotSameAs(grade3);
        assertThat(pGrade4).isNotNull().isNotSameAs(grade4);

        var higherGradesSubject = (ArrayList<Grade>) gradeRepository.findHighestGradesBySubject((float) 13.1, subject2);
        var higherGradesSubjectR = new ArrayList<>(Arrays.asList(grade2));
        assertThat(higherGradesSubject.size()).isEqualTo(higherGradesSubjectR.size());
        for(int i = 0; i < higherGradesSubject.size(); i++){
            assertThat(higherGradesSubject.get(i).getId()).isEqualTo(higherGradesSubjectR.get(i).getId());
            assertThat(higherGradesSubject.get(i).getValue()).isEqualTo(higherGradesSubjectR.get(i).getValue());
            assertThat(higherGradesSubject.get(i).getWeight()).isEqualTo(higherGradesSubjectR.get(i).getWeight());
            assertThat(higherGradesSubject.get(i).getClass()).isEqualTo(higherGradesSubjectR.get(i).getClass());
            assertThat(higherGradesSubject.get(i).getSubject().getId()).isEqualTo(higherGradesSubjectR.get(i).getSubject().getId());
            assertThat(higherGradesSubject.get(i).getSubject().getName()).isEqualTo(higherGradesSubjectR.get(i).getSubject().getName());
        }
    }

}
