package analyzer.repository;

import entity.competitor.teamGame.TeamGame;

import java.util.List;

public interface TeamGameRepository extends Repository<TeamGame>{

    TeamGame get(Long id);

    List<TeamGame> getAll();

    TeamGame add(TeamGame teamGame);

    void update(TeamGame teamGame);

    void delete(Long id);
}
