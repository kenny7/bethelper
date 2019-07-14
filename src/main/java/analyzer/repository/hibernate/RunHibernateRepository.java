package analyzer.repository.hibernate;

import analyzer.repository.RunRepository;
import entity.score.Run;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Data
@AllArgsConstructor
public class RunHibernateRepository implements RunRepository {

    /*EntityManager em;

    public RunHibernateRepository() {
        em = emFactory.createEntityManager();
    }*/

    @Override
    public Run get(Long id) {
        return em.find(Run.class, id);
    }

    @Override
    public List<Run> getAll() {
        TypedQuery<Run> namedQuery = em.createNamedQuery("run.getAll", Run.class);
        return namedQuery.getResultList();
    }

    @Override
    public Run add(Run run) {
        em.getTransaction().begin();
        Run result = em.merge(run);
        em.getTransaction().commit();
        return result;
    }

    @Override
    public void update(Run run) {
        em.getTransaction().begin();
        em.merge(run);
        em.getTransaction().commit();
    }

    @Override
    public void delete(Long id) {
        em.getTransaction().begin();
        em.remove(get(id));
        em.getTransaction().commit();
    }
}
