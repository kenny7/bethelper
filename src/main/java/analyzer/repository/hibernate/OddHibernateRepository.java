package analyzer.repository.hibernate;


import analyzer.repository.OddRepository;
import entity.odd.Odd;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Data
@AllArgsConstructor
public class OddHibernateRepository implements OddRepository {

    /*EntityManager em;

    public OddHibernateRepository() {
        em = emFactory.createEntityManager();
    }*/

    @Override
    public Odd get(Long id) {
        return em.find(Odd.class, id);
    }

    @Override
    public List<Odd> getAll() {
        TypedQuery<Odd> namedQuery = em.createNamedQuery("odd.getAll", Odd.class);
        return namedQuery.getResultList();
    }

    @Override
    public Odd add(Odd odd) {
        em.getTransaction().begin();
        Odd result = em.merge(odd);
        em.getTransaction().commit();
        return result;
    }

    @Override
    public void update(Odd odd) {
        em.getTransaction().begin();
        em.merge(odd);
        em.getTransaction().commit();
    }

    @Override
    public void delete(Long id) {
        em.remove(get(id));
    }
}
