package analyzer.repository.hibernate;

import analyzer.repository.MLBEventRepository;
import entity.event.MLBEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.RollbackException;
import javax.persistence.TypedQuery;
import java.util.List;

@Data
@AllArgsConstructor
public class MLBEventHibernateRepository implements MLBEventRepository {

    @Override
    public MLBEvent get(Long id) {
        return em.find(MLBEvent.class, id);
    }

    @Override
    public List<MLBEvent> getAll() {
        TypedQuery<MLBEvent> namedQuery = em.createNamedQuery("mlbevent.getAll", MLBEvent.class);
        return namedQuery.getResultList();
    }

    @Override
    public MLBEvent add(MLBEvent mlbEvent) {
        try {
            em.getTransaction().begin();
            MLBEvent result = em.merge(mlbEvent);
            em.getTransaction().commit();
            return result;
        } catch (RollbackException e){

            return null;
        }
    }

    @Override
    public void update(MLBEvent mlbEvent) {
        em.getTransaction().begin();
        em.merge(mlbEvent);
        em.getTransaction().commit();
    }

    @Override
    public void delete(Long id) {
        em.getTransaction().begin();
        em.remove(get(id));
        em.getTransaction().commit();
    }
}
