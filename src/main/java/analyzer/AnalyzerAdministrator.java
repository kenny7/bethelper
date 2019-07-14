package analyzer;

import analyzer.teamGameCreator.TeamGameCreator;
import entity.competitor.Team;
import entity.competitor.teamGame.EventResult;
import entity.competitor.teamGame.TeamGame;
import entity.event.MLBEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnalyzerAdministrator {

    private DatabaseAdministrator databaseAdministrator;
    private TeamGameCreator teamGameCreator;

    public List<TeamGame> createTwoTeamGamesFromMLBEvent(MLBEvent event) {
        return teamGameCreator.createTwoTeamGames(event);
    }

    public List<TeamGame> createTeamGames(List<MLBEvent> events) {
        List<TeamGame> teamGames = new LinkedList<>();

        for (MLBEvent event : events) {
            teamGames.addAll(createTwoTeamGamesFromMLBEvent(event));
        }

        return teamGames;
    }

    public Double calculateWinPercent(Team team, LocalDateTime startDate, LocalDateTime finishDate) {

        int gameCount = 0;
        int winCount = 0;
        Double winPercent = 0.0;

        List<TeamGame> temp = databaseAdministrator
                .selectTeamGamesBetweenDatesByTeam(team, startDate, finishDate);
        gameCount = temp.size();

        temp = databaseAdministrator
                .selectTeamGameBetweenDatesByTeamAndResult(team, EventResult.WIN, startDate, finishDate);
        winCount = temp.size();

        winPercent = new Double(winCount) / new Double(gameCount);

        return winPercent;

    }

    public TeamGame calculateAndSetWinPercent(TeamGame teamGame, LocalDateTime startDate) {

        Team team = teamGame.getTeam().get();
        LocalDateTime finishDate = teamGame.getDate().minusHours(1);

        Double winPercent = calculateWinPercent(team, startDate, finishDate);
        teamGame.setWinPercent(winPercent);

        return teamGame;
    }

    public List<TeamGame> calculateAndSetWinPercentForTeamGameList(List<TeamGame> teamGames, LocalDateTime startDate) {

        List<TeamGame> result = new LinkedList<>();

        for (TeamGame teamGame : teamGames) {
            result.add(calculateAndSetWinPercent(teamGame, startDate));
        }

        return result;
    }

    public Double calculateWinPercentForLastNGames(Team team, int n,LocalDateTime startDate, LocalDateTime finishDate) {

        int gameCount = 0;
        int winCount = 0;
        Double winPercent = 0.0;

        List<TeamGame> teamGames = databaseAdministrator
                .selectTeamGamesBetweenDatesByTeam(team, startDate, finishDate);

        List<TeamGame> temp = getLastNTeamGames(teamGames, n);
        gameCount = temp.size();

        temp = getTeamGameWithWinResult(teamGames);
        winCount = temp.size();

        winPercent = new Double(winCount) / new Double(gameCount);

        return winPercent;
    }

    public List<TeamGame> getTeamGameWithWinResult(List<TeamGame> teamGames){

        List<TeamGame> result = new LinkedList<>();

        Iterator<TeamGame> iterator = teamGames.iterator();

        while (iterator.hasNext()){
            TeamGame teamGame = iterator.next();
            if(teamGame.getResult().get() == EventResult.WIN)
                result.add(teamGame);
        }

        return result;
    }

    public List<TeamGame> getLastNTeamGames(List<TeamGame> teamGames, int n){

        List<TeamGame> result = new LinkedList<>();

        if(teamGames.size() > n){
            ListIterator<TeamGame> iterator = teamGames.listIterator(teamGames.size());
            int counter = 0;

            System.out.println(iterator.previousIndex());

            iterator.previousIndex();
            while (counter < n){
                counter++;
                TeamGame teamGame = iterator.previous();
                result.add(teamGame);
            }
        } else {
            result = teamGames;
        }
        return result;
    }

}
