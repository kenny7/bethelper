package analyzer.repository;

import analyzer.repository.hibernate.TeamHibernateRepository;
import entity.competitor.Team;
import org.junit.Test;

import java.util.List;

public class TeamRepositoryTest {

    TeamRepository repository = new TeamHibernateRepository();

    @Test
    public void get() {
        Team team = repository.get(1l);
        System.out.println(team);
    }

    @Test
    public void getAll() {

        List<Team> teams = repository.getAll();
        System.out.println(teams);
    }

    @Test
    public void add() {

        Team team = Team.builder()
                .name("Baltimore Orioles")
                .build();
        repository.add(team);

    }

    @Test
    public void update() {

        Team team = Team.builder()
                //.id(117l)
                .name("Cleveland Indians")
                .build();
        repository.update(team);

    }

    @Test
    public void delete() {
        repository.delete(4l);
    }

    @Test
    public void selectByNameTest(){
        System.out.println(repository.selectTeamByName("Arizona Diamondbacks"));
    }
}