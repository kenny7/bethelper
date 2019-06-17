package analyzer.repository;

import entity.competitor.Team;

import java.util.List;

public interface TeamRepository {

    Team selectTeamById(Long id);

    List<Team> selectAll();

    void writeTeam(Team team);

    void update(Team team);

    void deleteById(Long id);
}
