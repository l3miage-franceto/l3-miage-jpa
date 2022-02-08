package fr.uga.im2ag.l3.miage.db.repository.impl;

import fr.uga.im2ag.l3.miage.db.repository.api.GraduationClassRepository;
import fr.uga.im2ag.l3.miage.db.model.GraduationClass;

import javax.persistence.EntityManager;
import java.util.List;

public class GraduationClassRepositoryImpl extends BaseRepositoryImpl implements GraduationClassRepository {

    public GraduationClassRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public GraduationClass findByYearAndName(Integer year, String name) {
        // TODO
        return entityManager.createNamedQuery("GraduationClass.findByYearAndName", GraduationClass.class)
                .setParameter("name", name)
                .setParameter("year", year)
                .getResultList()
                .get(0);
    }

    @Override
    public void save(GraduationClass entity) {
        entityManager.persist(entity);
    }

    @Override
    public void delete(GraduationClass entity) {
        entityManager.remove(entity);
    }

    @Override
    public GraduationClass findById(Long id) {
        // TODO
        return entityManager.createNamedQuery("GraduationClass.findById", GraduationClass.class)
                .setParameter("id", id)
                .getResultList()
                .get(0);
    }

    @Override
    public List<GraduationClass> getAll() {
        // TODO
        return entityManager.createNamedQuery("GraduationClass.getAll", GraduationClass.class).getResultList();
    }
}
