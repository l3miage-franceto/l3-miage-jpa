package fr.uga.im2ag.l3.miage.db.repository;

import fr.uga.im2ag.l3.miage.db.repository.api.GraduationClassRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        final var gradClass1 = Fixtures.createClass();
        final var gradClass2 = Fixtures.createClass();

        entityManager.getTransaction().begin();
        classRepository.save(gradClass1);
        classRepository.save(gradClass2);
        entityManager.getTransaction().commit();
    }


    @Test
    void shouldFindByYearAndName() {
        // TODO
    }

}
