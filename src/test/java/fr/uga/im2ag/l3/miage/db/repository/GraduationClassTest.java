package fr.uga.im2ag.l3.miage.db.repository;

import fr.uga.im2ag.l3.miage.db.repository.api.GraduationClassRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class GraduationClassTest extends Base {

    GraduationClassRepository classRepository;

    @BeforeEach
    void before() {
        classRepository = daoFactory.newGraduationClassRepository(entityManager);
    }

    @AfterEach
    void after() {
        if (entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().rollback();
        }
    }

    @Test
    void shouldSaveClass() {
        // TODO
        final var class1 = Fixtures.createClass();
        final var class2 = Fixtures.createClass();

        entityManager.getTransaction().begin();
        classRepository.save(class1);
        classRepository.save(class2);
        entityManager.getTransaction().commit();

        entityManager.detach(class1);
        entityManager.detach(class2);

        var pClass1 = classRepository.findById(class1.getId());
        assertThat(pClass1).isNotNull().isNotSameAs(class1).isEqualTo(class1);

        var pClass2 = classRepository.findById(class2.getId());
        assertThat(pClass2).isNotNull().isNotSameAs(class2).isEqualTo(class2);
    }


    @Test
    void shouldFindByYearAndName() {
        // TODO
        final var class1 = Fixtures.createClass();
        class1.setName("BDSI");
        class1.setYear(2022);
        final var class2 = Fixtures.createClass();
        class2.setName("Prolog");
        class2.setYear(1978);
        final var class3 = Fixtures.createClass();
        class3.setName("Angular");
        class3.setYear(2019);

        entityManager.getTransaction().begin();
        classRepository.save(class1);
        classRepository.save(class2);
        classRepository.save(class3);
        entityManager.getTransaction().commit();

        entityManager.detach(class1);
        entityManager.detach(class2);
        entityManager.detach(class3);

        var pClassBDSI2022 = classRepository.findByYearAndName(2022, "BDSI");
        assertThat(pClassBDSI2022).isNotNull().isNotSameAs(class1).isEqualTo(class1);

        var pClassProlog1978 = classRepository.findByYearAndName(1978, "Prolog");
        assertThat(pClassProlog1978).isNotNull().isNotSameAs(class2).isEqualTo(class2);

        var pClassAngular2019 = classRepository.findByYearAndName(2019, "Angular");
        assertThat(pClassAngular2019).isNotNull().isNotSameAs(class3).isEqualTo(class3);

    }

}
