package analyzer.repository.hibernate;

import analyzer.repository.MLBEventRepository;
import entity.competitor.Team;
import entity.event.MLBEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.internal.SessionImpl;
import org.postgresql.util.PSQLException;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class MLBEventHibernateRepository implements MLBEventRepository {

    /*EntityManager em;

    public MLBEventHibernateRepository() {
        em = emFactory.createEntityManager();
    }*/

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
        }catch (RollbackException e){
            e.printStackTrace();
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

    @Override
    public List<MLBEvent> selectMLBEventBetweenDate(LocalDateTime startDate, LocalDateTime finishDate) {
        Query query = em.createQuery(
                "select i from mlbEvent i where i.timestamp > :startDate and i.timestamp < :finishDate")
                .setParameter("startDate", startDate)
                .setParameter("finishDate", finishDate);

        List<MLBEvent> events = (List<MLBEvent>) query.getResultList();

        return events;
    }
}
