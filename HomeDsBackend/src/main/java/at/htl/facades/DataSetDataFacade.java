package at.htl.facades;

import at.htl.model.DataSetData;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@ApplicationScoped
public class DataSetDataFacade {

    @PersistenceContext
    EntityManager entityManager;

    public void Save(DataSetData data) {
        entityManager.persist(data);
    }

    public DataSetData findById(long id) {
        return entityManager.find(DataSetData.class, id);
    }
}
