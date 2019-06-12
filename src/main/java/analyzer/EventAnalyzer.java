package analyzer;

import analyzer.inputData.BaseballGameInput;
import analyzer.outputData.TeamGameOutput;
import competitor.TeamGame;
import competitor.indicator.HomeOrAway;
import competitor.indicator.WinOrLose;
import event.BaseballGame;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventAnalyzer {

    private BaseballGameInput baseballGameInput;
    private TeamGameOutput outputData;
    private List<TeamGame> teamGames;

    public TeamGame createFirstTeamGame(BaseballGame baseballGame){

        TeamGame teamGame = TeamGame.teamGameBuilder()
                .name(baseballGame.getTeam1().getName())
                .opponent(baseballGame.getTeam2())
                .localDate(baseballGame.getLocalDate())
                .runs(baseballGame.getFirstTeamRun())
                .missedRuns(baseballGame.getSecondTeamRun())
                .place(HomeOrAway.HOME)
                .coefficientOfWin(baseballGame.getCoefficientOfWin1())
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

    public void createTeamGames(){
        for (BaseballGame baseballGame : baseballGameInput.getBaseballGames()){
            teamGames.add(createFirstTeamGame(baseballGame));
            teamGames.add(createSecondTeamGame(baseballGame));
        }
    }

    public void setTeamGameInOutputData(){
        outputData.setTeamGames(teamGames);
    }

}
