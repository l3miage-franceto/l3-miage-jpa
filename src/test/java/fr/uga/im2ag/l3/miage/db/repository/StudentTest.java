package fr.uga.im2ag.l3.miage.db.repository;

import fr.uga.im2ag.l3.miage.db.model.Student;
import fr.uga.im2ag.l3.miage.db.repository.api.StudentRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;


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
        final var s1Grade1 = Fixtures.createGrade(null).setValue(13.4F).setWeight(2F);
        final var s1Grade2 = Fixtures.createGrade(null).setValue(14.1F).setWeight(2F);
        final var s1Grade3 = Fixtures.createGrade(null).setValue(18.9F).setWeight(1F);
        student1.setGrades(Arrays.asList(s1Grade1, s1Grade2, s1Grade3));
        //avg = 14.78
        //avg sans weight = 15.47

        final var student2 = Fixtures.createStudent(class1);
        final var s2Grade1 = Fixtures.createGrade(null).setValue(9.4F).setWeight(1F);
        final var s2Grade2 = Fixtures.createGrade(null).setValue(10.56F).setWeight(1F);
        final var s2Grade3 = Fixtures.createGrade(null).setValue(11.02F).setWeight(3F);
        student2.setGrades(Arrays.asList(s2Grade1, s2Grade2, s2Grade3));
        //avg = 10.604
        //avg sans weight = 10.33

        final var class2 = Fixtures.createClass();
        final var student3 = Fixtures.createStudent(class2);
        final var s3Grade1 = Fixtures.createGrade(null).setValue(15.0F).setWeight(3F);
        final var s3Grade2 = Fixtures.createGrade(null).setValue(14.9F).setWeight(1F);
        final var s3Grade3 = Fixtures.createGrade(null).setValue(17.58F).setWeight(1F);
        student3.setGrades(Arrays.asList(s3Grade1, s3Grade2, s3Grade3));
        //avg = 15.496
        //avg sans weight = 15.83

        entityManager.getTransaction().begin();
        entityManager.persist(class1);
        entityManager.persist(class2);

        studentRepository.save(student1);
        studentRepository.save(student2);
        studentRepository.save(student3);
        entityManager.getTransaction().commit();

        entityManager.detach(student1);
        entityManager.detach(student2);
        entityManager.detach(student3);

        var pStudentsAvg = (ArrayList<Student>) studentRepository.findStudentHavingGradeAverageAbove(14.7F);
        assertThat(pStudentsAvg).isNotEmpty();
        ArrayList<Student> pStudentAvgRep = new ArrayList<Student>(Arrays.asList(student1, student3));
        assertThat(pStudentsAvg).isEqualTo(pStudentAvgRep);

    }

}
