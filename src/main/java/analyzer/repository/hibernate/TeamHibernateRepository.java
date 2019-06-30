package analyzer.repository.hibernate;

import analyzer.repository.TeamRepository;
import entity.competitor.Team;

import javax.persistence.Query;
import javax.persistence.RollbackException;
import javax.persistence.TypedQuery;
import java.util.List;

public class TeamHibernateRepository implements TeamRepository {

    //private EntityManager em = Persistence.createEntityManagerFactory("baseball").createEntityManager();

    @Override
    public Team get(Long id) {
        return em.find(Team.class, id);
    }

    @Override
    public List<Team> getAll() {
        TypedQuery<Team> namedQuery = em.createNamedQuery("team.getAll", Team.class);
        return namedQuery.getResultList();
    }

    @Override
    public Team add(Team team) {
        try {
            em.getTransaction().begin();
            Team result = em.merge(team);
            em.getTransaction().commit();
            return result;
        }catch (RollbackException e){

            return null;
        }
    }

    @Override
    public void update(Team team) {
        try {
            em.getTransaction().begin();
            em.merge(team);
            em.getTransaction().commit();
        }catch (RollbackException e){

        }
    }

    @Override
    public void delete(Long id) {
        em.getTransaction().begin();
        em.remove(get(id));
        em.getTransaction().commit();
    }

    @Override
    public Team selectTeamByName(String s){
        Query query = em.createQuery("select i from team i where i.name =: name ").setParameter("name", s);
        return (Team) query.getSingleResult();
    }
}
