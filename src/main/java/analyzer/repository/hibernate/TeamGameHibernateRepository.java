package analyzer.repository.hibernate;

import analyzer.repository.TeamGameRepository;
import entity.competitor.teamGame.TeamGame;

import javax.persistence.TypedQuery;
import java.util.List;

public class TeamGameHibernateRepository implements TeamGameRepository {

    @Override
    public List<TeamGame> getAll() {
        TypedQuery<entity.competitor.teamGame.TeamGame> namedQuery
                = em.createNamedQuery("teamGame.getAll", TeamGame.class);
        return namedQuery.getResultList();
    }

    @Override
    public TeamGame add(TeamGame teamGame) {
        em.getTransaction().begin();
        TeamGame result = em.merge(teamGame);
        em.getTransaction().commit();
        return result;
    }

    @Override
    public void update(TeamGame teamGame) {
        em.getTransaction().begin();
        em.merge(teamGame);
        em.getTransaction().commit();
    }

    @Override
    public void delete(Long id) {
        em.remove(get(id));
    }

    @Override
    public TeamGame get(Long id) {
        return em.find(TeamGame.class, id);
    }
}
