package analyzer.repository;

import analyzer.repository.hibernate.RunHibernateRepository;
import analyzer.repository.hibernate.TeamHibernateRepository;
import entity.competitor.Team;
import entity.score.Run;
import org.junit.Test;

import java.util.List;

public class RunRepositoryTest {

    RunRepository repository = new RunHibernateRepository();
    TeamRepository teamRepository = new TeamHibernateRepository();

    @Test
    public void get() {
        Run run = repository.get(10l);
        System.out.println(run);
    }

    @Test
    public void getAll() {
        List<Run> result = repository.getAll();
        System.out.println(result);
    }

    @Test
    public void add() {

        Team team = teamRepository.get(1l);
        System.out.println(team + " " + team.getId());
        Run run = Run.builder().team(team).build();
        repository.add(run);
    }

    @Test
    public void update() {
        Run run = repository.get(11l);
        run.setTeam(teamRepository.get(2l));
        repository.update(run);
    }

    @Test
    public void delete() {
        repository.delete(11l);
    }
}