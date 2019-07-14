package analyzer.repository;

import analyzer.repository.hibernate.TeamGameHibernateRepository;
import analyzer.repository.hibernate.TeamHibernateRepository;
import entity.competitor.teamGame.EventResult;
import entity.competitor.teamGame.HomeOrAway;
import entity.competitor.teamGame.TeamGame;
import org.junit.Test;

public class TeamGameRepositoryTest {

    TeamGameRepository teamGameRepository = new TeamGameHibernateRepository();
    TeamRepository teamRepository = new TeamHibernateRepository();

    @Test
    public void getAll() {
        System.out.println(teamGameRepository.getAll());
    }

    @Test
    public void add() {

        TeamGame teamGame = TeamGame.builder()
                .team(teamRepository.get(1l))
                .opponent(teamRepository.get(2l))
                .place(HomeOrAway.HOME)
                .runs(5)
                .missedRuns(4)
                .result(EventResult.WIN)
                .winCoefficient(new Double(1.56))
                .winPercent(new Double(0.789))
                .build();

        teamGameRepository.add(teamGame);

    }

    @Test
    public void update() {
    }

    @Test
    public void delete() {

    }

    @Test
    public void get() {
        System.out.println(teamGameRepository.get(43l));
    }
}