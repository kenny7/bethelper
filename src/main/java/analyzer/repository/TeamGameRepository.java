package analyzer.repository;

import entity.competitor.Team;
import entity.competitor.teamGame.EventResult;
import entity.competitor.teamGame.TeamGame;

import java.time.LocalDateTime;
import java.util.List;

public interface TeamGameRepository extends Repository<TeamGame>{

    TeamGame get(Long id);

    List<TeamGame> getAll();

    TeamGame add(TeamGame teamGame);

    void update(TeamGame teamGame);

    void delete(Long id);

    List<TeamGame> selectTeamGamesBetweenDate(
            LocalDateTime startDate, LocalDateTime finishDate);

    List<TeamGame> selectTeamGamesBetweenDatesByTeamId
            (Long id, LocalDateTime startDate, LocalDateTime finishDate);

    List<TeamGame> selectTeamGamesBetweenDatesByTeam
            (Team team, LocalDateTime startDate, LocalDateTime finishDate);

    List<TeamGame> selectTeamGamesBetweenDatesByIdAndResult
            (Long id, EventResult result, LocalDateTime startDate, LocalDateTime finishDate);

    List<TeamGame> selectTeamGamesBetweenDatesByTeamAndResult
            (Team team, EventResult result, LocalDateTime startDate, LocalDateTime finishDate);
}
