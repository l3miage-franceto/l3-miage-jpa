package fr.uga.im2ag.l3.miage.db.repository;

import fr.uga.im2ag.l3.miage.db.model.Student;
import fr.uga.im2ag.l3.miage.db.repository.api.StudentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class StudentTest extends Base {

    StudentRepository studentRepository;

    @BeforeEach
    void before() {
        studentRepository = daoFactory.newStudentRepository(entityManager);
    }

    @AfterEach
    void after() {
        if (entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().rollback();
        }
    }

    @Test
    void shouldSaveStudent() {
        // TODO
        final var class1 = Fixtures.createClass();
        final var student1 = Fixtures.createStudent(class1);
        final var student2 = Fixtures.createStudent(class1);
        final var class2 = Fixtures.createClass();
        final var student3 = Fixtures.createStudent(class2);

        entityManager.getTransaction().begin();
        entityManager.persist(class1);
        entityManager.persist(class2);
        studentRepository.save(student1);
        studentRepository.save(student2);
        studentRepository.save(student3);
        entityManager.getTransaction().commit();

        entityManager.detach(class1);
        entityManager.detach(class2);
        entityManager.detach(student1);
        entityManager.detach(student2);
        entityManager.detach(student3);

        var pStudent1 = studentRepository.findById(student1.getId());
        assertThat(pStudent1).isNotNull().isNotSameAs(student1).isEqualTo(student1);
        var pStudent2 = studentRepository.findById(student2.getId());
        assertThat(pStudent2).isNotNull().isNotSameAs(student2).isEqualTo(student2);
        var pStudent3 = studentRepository.findById(student3.getId());
        assertThat(pStudent3).isNotNull().isNotSameAs(student3).isEqualTo(student3);
    }

    @Test
    void shouldFindStudentHavingGradeAverageAbove() {
        // TODO
        final var class1 = Fixtures.createClass();
        final var student1 = Fixtures.createStudent(class1);
        final var s1Grade1 = Fixtures.createGrade(null).setValue((float)13.4).setWeight((float)2);
        final var s1Grade2 = Fixtures.createGrade(null).setValue((float)14.1).setWeight((float)2);
        final var s1Grade3 = Fixtures.createGrade(null).setValue((float)18.9).setWeight((float)1);
        student1.setGrades(Arrays.asList(s1Grade1, s1Grade2, s1Grade3));
        //avg = 14.78
        //avg sans weight = 15.47

        final var student2 = Fixtures.createStudent(class1);
        final var s2Grade1 = Fixtures.createGrade(null).setValue((float)9.4).setWeight((float)1);
        final var s2Grade2 = Fixtures.createGrade(null).setValue((float)10.56).setWeight((float)1);
        final var s2Grade3 = Fixtures.createGrade(null).setValue((float)11.02).setWeight((float)3);
        student2.setGrades(Arrays.asList(s2Grade1, s2Grade2, s2Grade3));
        //avg = 10.604
        //avg sans weight = 10.33

        final var class2 = Fixtures.createClass();
        final var student3 = Fixtures.createStudent(class2);
        final var s3Grade1 = Fixtures.createGrade(null).setValue((float)15.0).setWeight((float)3);
        final var s3Grade2 = Fixtures.createGrade(null).setValue((float)14.9).setWeight((float)1);
        final var s3Grade3 = Fixtures.createGrade(null).setValue((float)17.58).setWeight((float)1);
        student3.setGrades(Arrays.asList(s3Grade1, s3Grade2, s3Grade3));
        //avg = 15.496
        //avg sans weight = 15.83

        entityManager.getTransaction().begin();
        entityManager.persist(class1);
        entityManager.persist(class2);

        entityManager.persist(s1Grade1);
        entityManager.persist(s1Grade2);
        entityManager.persist(s1Grade3);

        entityManager.persist(s2Grade1);
        entityManager.persist(s2Grade2);
        entityManager.persist(s2Grade3);

        entityManager.persist(s3Grade1);
        entityManager.persist(s3Grade2);
        entityManager.persist(s3Grade3);

        studentRepository.save(student1);
        studentRepository.save(student2);
        studentRepository.save(student3);
        entityManager.getTransaction().commit();

        entityManager.detach(class1);
        entityManager.detach(class2);
        entityManager.detach(student1);
        entityManager.detach(student2);
        entityManager.detach(student3);

        var pStudentsAvg = studentRepository.findStudentHavingGradeAverageAbove((float)14.7);
        assertThat(pStudentsAvg).isNotNull();
        ArrayList<Student> pStudentAvgRep = new ArrayList<Student>(Arrays.asList(student1, student3));
        assertThat(pStudentsAvg).isEqualTo(pStudentAvgRep);

    }

}
