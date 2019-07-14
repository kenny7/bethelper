package analyzer.repository.hibernate;

import analyzer.repository.TeamGameRepository;
import entity.competitor.Team;
import entity.competitor.teamGame.EventResult;
import entity.competitor.teamGame.TeamGame;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.List;

public class TeamGameHibernateRepository implements TeamGameRepository {

    /*EntityManager em;

    public TeamGameHibernateRepository() {
        em = emFactory.createEntityManager();
    }*/

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

    @Override
    public List<TeamGame> selectTeamGamesBetweenDate(
            LocalDateTime startDate, LocalDateTime finishDate){

        Query query = em.createQuery(
                "select i from teamGame i where i.date > :startDate and i.date < :finishDate")
                .setParameter("startDate", startDate)
                .setParameter("finishDate", finishDate);

        return (List<TeamGame>) query.getResultList();
    }

    @Override
    public List<TeamGame> selectTeamGamesBetweenDatesByTeamId(
            Long id, LocalDateTime startDate, LocalDateTime finishDate) {

        Query query = em.createQuery(
                "select i from teamGame i " +
                        "where i.date > :startDate " +
                        "and i.date < :finishDate " +
                        "and i.team.id = :id")
                .setParameter("startDate", startDate)
                .setParameter("finishDate", finishDate)
                .setParameter("id", id);


        return (List<TeamGame>) query.getResultList();
    }

    @Override
    public List<TeamGame> selectTeamGamesBetweenDatesByIdAndResult
            (Long id, EventResult result, LocalDateTime startDate, LocalDateTime finishDate) {

        Query query = em.createQuery(
                "select i from teamGame i " +
                        "where i.date > :startDate " +
                        "and i.date < :finishDate " +
                        "and i.id = :id " +
                        "and i.result = :result")
                .setParameter("startDate", startDate)
                .setParameter("finishDate", finishDate)
                .setParameter("id", id)
                .setParameter("result", result);


        return (List<TeamGame>) query.getResultList();
    }

    @Override
    public List<TeamGame> selectTeamGamesBetweenDatesByTeam
            (Team team, LocalDateTime startDate, LocalDateTime finishDate){

        Query query = em.createQuery(
                "select i from teamGame i " +
                        "where i.date > :startDate " +
                        "and i.date < :finishDate " +
                        "and i.team = :team " +
                        "order by date")
                .setParameter("startDate", startDate)
                .setParameter("finishDate", finishDate)
                .setParameter("team", team);

        return (List<TeamGame>) query.getResultList();
    }

    @Override
    public List<TeamGame> selectTeamGamesBetweenDatesByTeamAndResult
            (Team team, EventResult result, LocalDateTime startDate, LocalDateTime finishDate) {

        Query query = em.createQuery(
                "select i from teamGame i " +
                        "where i.date > :startDate " +
                        "and i.date < :finishDate " +
                        "and i.team = :team " +
                        "and i.result = :result")
                .setParameter("startDate", startDate)
                .setParameter("finishDate", finishDate)
                .setParameter("team", team)
                .setParameter("result", result);


        return (List<TeamGame>) query.getResultList();
    }
}
