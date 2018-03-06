package at.htl.facades;

import at.htl.model.Campaign;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class CampaignFacade {

    @PersistenceContext
    EntityManager entityManager;

    public void save(Campaign campaign) {
        entityManager.persist(campaign);
    }

    public void merge(Campaign campaign) {
        entityManager.merge(campaign);
    }

    public Campaign findById(long id) {
        return entityManager.find(Campaign.class,id);
    }

    public List<Campaign> getAll() {
        TypedQuery<Campaign> query = entityManager.createNamedQuery("Campaign.GetAll",Campaign.class);
        return query.getResultList();
    }

    public void delete(long id) {
        entityManager.remove(findById(id));
    }

    public List<Campaign> getAllDataSet() {
        TypedQuery<Campaign> query = entityManager.createNamedQuery("Campaign.GetAllDataSet",Campaign.class);
        return query.getResultList();
    }

    public List<Campaign> getAllMedia() {
        TypedQuery<Campaign> query = entityManager.createNamedQuery("Campaign.GetAllMedia",Campaign.class);
        return query.getResultList();
    }

}
