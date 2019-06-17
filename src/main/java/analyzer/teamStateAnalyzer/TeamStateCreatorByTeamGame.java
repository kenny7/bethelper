package analyzer.teamStateAnalyzer;

import entity.competitor.Team;
import entity.competitor.TeamGame;
import entity.competitor.TeamState;
import entity.competitor.indicator.WinOrLose;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import repositories.TeamGameTextFileRepository;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeamStateCreatorByTeamGame {

    private List<TeamGame> teamGames = new LinkedList<>();
    private TeamGameTextFileRepository repository;

    public List<TeamState> calculateTeamStatesForAllTeamGames(){
        List<TeamState> teamStates = new LinkedList<>();
        for(TeamGame teamGame : teamGames){
            TeamState teamState = createTeamStateForDate(teamGame, teamGame.getLocalDate());
            teamStates.add(teamState);
        }
        return teamStates;
    }

    public TeamState createTeamStateForDate(Team team, LocalDate date){

        TeamState teamState = TeamState.teamStateBuilder()
                .name(team.getName())
                .localDate(date)
                .build();

        int countOfGames = calculateCountOfGames(date, team);
        teamState.setGamesCount(countOfGames);
        int countOfWins = calculatedCountOfWins(date, team);
        teamState.setWinsCount(countOfWins);
        teamState.setWinPercent(calculateWinPercent(countOfGames, countOfWins));
        int countOfLoses = calculateCountOfLoses(date, team);
        teamState.setLosesCount(countOfLoses);

        //todo develop calculating other indicators

        return teamState;

    }

    public Integer calculateCountOfGames(LocalDate date, Team team){

        List<TeamGame> result = repository.selectTeamGamesByNameAndDateSortedByDate(team, date, true);

        return result.size();
    }

    public Integer calculatedCountOfWins(LocalDate date, Team team){

        List<TeamGame> result = repository.selectTeamGamesByNameAndDateAndResultSortedByDate(date, team, WinOrLose.WIN);

        return result.size();
    }

    public Integer calculateCountOfLoses(LocalDate date, Team team){

        List<TeamGame> result = repository.selectTeamGamesByNameAndDateAndResultSortedByDate(date, team, WinOrLose.LOSE);

        return result.size();
    }

    public Double calculateWinPercent(int gamesCount, int winCount){

        Double result = new Double(0);

        if(gamesCount != 0)
            result = new Double(winCount)/new Double(gamesCount);

        return result;
    }

}
