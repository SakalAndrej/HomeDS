package at.htl.facades;

import at.htl.model.DataSetDataField;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@ApplicationScoped
public class DataSetFieldFacade {


    @PersistenceContext
    EntityManager entityManager;

    public void Save(DataSetDataField dataField) {
        entityManager.persist(dataField);
    }

    public DataSetDataField findById(long id) {
        return entityManager.find(DataSetDataField.class, id);
    }

    public List<DataSetDataField> getAll() {
        TypedQuery<DataSetDataField> q = entityManager.createNamedQuery("GetAll", DataSetDataField.class);
        return q.getResultList();
    }
}
