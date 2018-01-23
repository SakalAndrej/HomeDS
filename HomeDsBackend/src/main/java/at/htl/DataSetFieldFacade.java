package at.htl;

import at.htl.model.DataSetDataField;

import javax.faces.bean.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
    
}
