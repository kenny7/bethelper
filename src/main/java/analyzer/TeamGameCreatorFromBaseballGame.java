package analyzer;

import entity.competitor.TeamGame;
import entity.competitor.indicator.HomeOrAway;
import entity.competitor.indicator.WinOrLose;
import entity.competitor.indicator.WinPercent;
import entity.event.BaseballGame;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamGameCreatorFromBaseballGame {

    private List<BaseballGame> baseballGames = new LinkedList<>();

    public List<TeamGame> createTeamGames(){
        List<TeamGame> teamGames = new LinkedList<>();
        for (BaseballGame baseballGame : baseballGames){
            teamGames.add(createFirstTeamGame(baseballGame));
            teamGames.add(createSecondTeamGame(baseballGame));
        }
        return teamGames;
    }

    public TeamGame createFirstTeamGame(BaseballGame baseballGame){

        TeamGame teamGame = TeamGame.teamGameBuilder()
                .name(baseballGame.getTeam1().getName())
                .opponent(baseballGame.getTeam2())
                .localDate(baseballGame.getLocalDate())
                .runs(baseballGame.getFirstTeamRun())
                .missedRuns(baseballGame.getSecondTeamRun())
                .place(HomeOrAway.HOME)
                .coefficientOfWin(baseballGame.getCoefficientOfWin1())
                .eventId(baseballGame.getId())
                .winPercent(new WinPercent(new Double(0)))
                .build();

        if(isTeamWinner(teamGame))
            teamGame.setResult(WinOrLose.WIN);
        else
            teamGame.setResult(WinOrLose.LOSE);

        return teamGame;
    }

    public TeamGame createSecondTeamGame(BaseballGame baseballGame){

        TeamGame teamGame = TeamGame.teamGameBuilder()
                .name(baseballGame.getTeam2().getName())
                .opponent(baseballGame.getTeam1())
                .localDate(baseballGame.getLocalDate())
                .runs(baseballGame.getSecondTeamRun())
                .missedRuns(baseballGame.getFirstTeamRun())
                .place(HomeOrAway.AWAY)
                .coefficientOfWin(baseballGame.getCoefficientOfWin2())
                .eventId(baseballGame.getId())
                .winPercent(new WinPercent(new Double(0)))
                .build();

        if(isTeamWinner(teamGame))
            teamGame.setResult(WinOrLose.WIN);
        else
            teamGame.setResult(WinOrLose.LOSE);

        return teamGame;
    }

    private boolean isTeamWinner(TeamGame teamGame){
        if(teamGame.getRuns().size() > teamGame.getMissedRuns().size())
            return true;
        else
            return false;
    }
}
