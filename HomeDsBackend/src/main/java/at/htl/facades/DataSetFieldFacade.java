package at.htl.facades;

import at.htl.model.DataSetDataField;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class DataSetFieldFacade {

    @PersistenceContext
    EntityManager entityManager;

    public void save(DataSetDataField dataField) {
        entityManager.persist(dataField);
    }

    public void update(DataSetDataField dataField) {
        DataSetDataField dataToUpdate = findById(dataField.getDataSetId());
        dataToUpdate.setTitle(dataField.getTitle());
        dataToUpdate.setValue(dataField.getValue());
    }

    public DataSetDataField findById(long id) {
        return entityManager.find(DataSetDataField.class, id);
    }

    public List<DataSetDataField> getAll() {
        TypedQuery<DataSetDataField> q = entityManager.createNamedQuery("DataSetDataField.GetAll", DataSetDataField.class);
        return q.getResultList();
    }

    public void delete(long getDataRowId) {
        DataSetDataField entityToDelete = findById(getDataRowId);
        entityManager.remove(entityToDelete);
    }
}
