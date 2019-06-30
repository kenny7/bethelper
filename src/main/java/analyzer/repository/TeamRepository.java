package analyzer.repository;

import entity.competitor.Team;

import java.util.List;

public interface TeamRepository extends Repository<Team>{

    Team get(Long id);

    List<Team> getAll();

    Team add(Team teamGame);

    void update(Team teamGame);

    void delete(Long id);

    Team selectTeamByName(String s);
}
