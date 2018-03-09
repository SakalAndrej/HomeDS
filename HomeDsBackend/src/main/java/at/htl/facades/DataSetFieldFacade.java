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
    private
    EntityManager entityManager;

    public void save(DataSetDataField dataField) {
        entityManager.persist(dataField);
    }

    public void merge(DataSetDataField dataField) {
        entityManager.merge(dataField);
    }

    public void update(DataSetDataField dataField) {
        DataSetDataField dataToUpdate = findByRowId(dataField.getDataRowId());
        dataToUpdate.setTitle(dataField.getTitle());
        dataToUpdate.setValue(dataField.getValue());
    }

    private DataSetDataField findByRowId(long rowId) {
        TypedQuery<DataSetDataField> q = entityManager.createNamedQuery("DataSetDataField.findByRowId", DataSetDataField.class).setParameter("id", rowId);
        return q.getSingleResult();
    }

    private DataSetDataField findById(long id) {
        return entityManager.find(DataSetDataField.class, id);
    }

    public List<DataSetDataField> getAll() {
        TypedQuery<DataSetDataField> q = entityManager.createNamedQuery("DataSetDataField.GetAll", DataSetDataField.class);
        return q.getResultList();
    }

    public void deleteByRowId(long getDataRowId) {
        if (getDataRowId > 0) {
            DataSetDataField entityToDelete = findByRowId(getDataRowId);
            entityManager.remove(entityToDelete);
        }
    }

    public void deleteById(long id) {
        DataSetDataField entityToDelete = findById(id);
        entityManager.remove(entityToDelete);
    }

    public List<DataSetDataField> getActiveDataSetRows() {
        TypedQuery<DataSetDataField> q = entityManager.createNamedQuery("DataSetDataField.findActive", DataSetDataField.class);
        return q.getResultList();
    }
}
