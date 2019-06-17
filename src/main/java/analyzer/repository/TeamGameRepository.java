package analyzer.repository;

import entity.competitor.TeamGame;

import java.util.List;

public interface TeamGameRepository {

    TeamGame selectTeamGameById(Long id);

    List<TeamGame> selectAll();

    void writeTeamGame(TeamGame teamGame);

    void update(TeamGame teamGame);

    void deleteById(Long id);
}
