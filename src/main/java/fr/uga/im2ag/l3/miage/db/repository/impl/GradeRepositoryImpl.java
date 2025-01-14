package fr.uga.im2ag.l3.miage.db.repository.impl;

import fr.uga.im2ag.l3.miage.db.repository.api.GradeRepository;
import fr.uga.im2ag.l3.miage.db.model.Grade;
import fr.uga.im2ag.l3.miage.db.model.Subject;

import javax.persistence.EntityManager;
import javax.persistence.NamedQuery;
import java.util.List;

public class GradeRepositoryImpl extends BaseRepositoryImpl implements GradeRepository {

    /**
     * Build a base repository
     *
     * @param entityManager the entity manager
     */
    public GradeRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public List<Grade> findHighestGrades(float limit) {
        // TODO
        return entityManager.createNamedQuery("Grade.findHighestGrades", Grade.class)
                .setParameter("limit", limit)
                .getResultList();
    }

    @Override
    public List<Grade> findHighestGradesBySubject(float limit, Subject subject) {
        // TODO
        return entityManager.createNamedQuery("Grade.findHighestGradeByStudent", Grade.class)
                .setParameter("subject", subject)
                .setParameter("limit", limit)
                .getResultList();
    }

    @Override
    public void updateGradeValue(long id) {
        entityManager.createNamedQuery("Grade.updateValue", Grade.class)
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    public void save(Grade entity) {
        entityManager.persist(entity);
    }

    @Override
    public void delete(Grade entity) {
        entityManager.remove(entity);
    }

    @Override
    public Grade findById(Long id) {
        return entityManager.find(Grade.class, id);
    }

    @Override
    public List<Grade> getAll() {
        // TODO
        return entityManager.createNamedQuery("Grade.getAll", Grade.class)
                .getResultList();
    }
}
