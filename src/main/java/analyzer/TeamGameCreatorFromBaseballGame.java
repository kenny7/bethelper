package analyzer;

import entity.competitor.TeamGame;
import entity.competitor.teamGame.HomeOrAway;
import entity.competitor.teamGame.EventResult;
import entity.competitor.indicator.WinPercent;
import entity.event.MLBEvent;
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

    private List<MLBEvent> MLBEvents = new LinkedList<>();

    public List<TeamGame> createTeamGames(){
        List<TeamGame> teamGames = new LinkedList<>();
        for (MLBEvent MLBEvent : MLBEvents){
            teamGames.add(createFirstTeamGame(MLBEvent));
            teamGames.add(createSecondTeamGame(MLBEvent));
        }
        return teamGames;
    }

    public TeamGame createFirstTeamGame(MLBEvent MLBEvent){

        TeamGame teamGame = TeamGame.teamGameBuilder()
                .name(MLBEvent.getTeam1().get().getName().get())
                .opponent(MLBEvent.getTeam2().get())
                .localDate(MLBEvent.getLocalDate())
                .runs(MLBEvent.getRuns().get())
                .missedRuns(MLBEvent.getSecondTeamRun())
                .place(HomeOrAway.HOME)
                .coefficientOfWin(MLBEvent.getCoefficientOfWin1())
                .eventId(MLBEvent.getId())
                .winPercent(new WinPercent(new Double(0)))
                .build();

        if(isTeamWinner(teamGame))
            teamGame.setResult(EventResult.WIN);
        else
            teamGame.setResult(EventResult.LOSE);

        return teamGame;
    }

    public TeamGame createSecondTeamGame(MLBEvent MLBEvent){

        TeamGame teamGame = TeamGame.teamGameBuilder()
                .name(MLBEvent.getTeam2().get().getName().get())
                .opponent(MLBEvent.getTeam1().get())
                .localDate(MLBEvent.getLocalDate())
                .runs(MLBEvent.getSecondTeamRun())
                .missedRuns(MLBEvent.getRuns().get())
                .place(HomeOrAway.AWAY)
                .coefficientOfWin(MLBEvent.getCoefficientOfWin2())
                .eventId(MLBEvent.getId())
                .winPercent(new WinPercent(new Double(0)))
                .build();

        if(isTeamWinner(teamGame))
            teamGame.setResult(EventResult.WIN);
        else
            teamGame.setResult(EventResult.LOSE);

        return teamGame;
    }

    private boolean isTeamWinner(TeamGame teamGame){
        if(teamGame.getRuns().size() > teamGame.getMissedRuns().size())
            return true;
        else
            return false;
    }
}
