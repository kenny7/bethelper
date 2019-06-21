package analyzer.teamStateAnalyzer;

import entity.competitor.Team;
import entity.competitor.TeamGame;
import entity.competitor.TeamGameWithTeamState;
import entity.competitor.TeamState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import repositories.TeamGameTextFileRepository;
import repositories.TeamStateRepository;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeamStateAnalyzer {

    private TeamGameTextFileRepository teamGameTextFileRepository;
    private TeamStateRepository teamStateRepository;
    private List<TeamGame> teamGames;

    public List<TeamGameWithTeamState> teamGameWithTeamStates(){

        List<TeamGameWithTeamState> teamGameWithTeamStates = new LinkedList<>();

        for(TeamGame teamGame : teamGames){

            Team team = Team.builder().name(teamGame.getName().get()).build();
            LocalDate date = teamGame.getLocalDate();
            teamGameWithTeamStates.add(createTeamGameWithTeamState(team, date));
        }

        return teamGameWithTeamStates;
    }

    public TeamGameWithTeamState createTeamGameWithTeamState(Team team, LocalDate date){

        TeamGameWithTeamState teamGameWithTeamState = TeamGameWithTeamState.teamGameWithTeamStateBuilder()
                .build();

        TeamGame teamGame = teamGameTextFileRepository.selectTeamGameByNameAndDate(team, date);

        teamGameWithTeamState.setName(teamGame.getName().get());
        teamGameWithTeamState.setOpponent(teamGame.getOpponent());
        teamGameWithTeamState.setLocalDate(teamGame.getLocalDate());
        teamGameWithTeamState.setRuns(teamGame.getRuns());
        teamGameWithTeamState.setMissedRuns(teamGame.getMissedRuns());
        teamGameWithTeamState.setCoefficientOfWin(teamGame.getCoefficientOfWin());
        teamGameWithTeamState.setPlace(teamGame.getPlace());
        teamGameWithTeamState.setResult(teamGame.getResult());

        teamGameWithTeamState.setTeamState(calculateTeamState(team, date));
        teamGameWithTeamState.setOpponentState(calculateTeamState(teamGame.getOpponent(), date));

        return teamGameWithTeamState;
    }

    private TeamState calculateTeamState(Team team, LocalDate date){

        TeamState teamState = teamStateRepository.selectTeamStateByNameAndDate(team, date);

        return teamState;
    }

}
