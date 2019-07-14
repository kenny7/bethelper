package analyzer.teamGameCreator;

import entity.competitor.Team;
import entity.competitor.teamGame.EventResult;
import entity.competitor.teamGame.HomeOrAway;
import entity.competitor.teamGame.TeamGame;
import entity.event.MLBEvent;
import entity.odd.Odd;
import entity.odd.Winner1;
import entity.odd.Winner2;
import entity.score.Run;
import lombok.Data;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

@Data
public class TeamGameCreator {

    public List<TeamGame> createTwoTeamGames(MLBEvent event){
        List<TeamGame> teamGames = new LinkedList<>();

        TeamGame firstTeamGame = TeamGame.builder()
                .date(event.getTimestamp().get())
                .team(event.getTeam1().get())
                .opponent(event.getTeam2().get())
                .place(HomeOrAway.HOME)
                .stage(event.getMLBStage())
                .runs(calculateRuns(event, event.getTeam1().get()))
                .missedRuns(calculateRuns(event, event.getTeam2().get()))
                .winCoefficient(calculateOdd(event, Winner1.class))
                .build();

        TeamGame secondTeamGame = TeamGame.builder()
                .date(event.getTimestamp().get())
                .team(event.getTeam2().get())
                .opponent(event.getTeam1().get())
                .place(HomeOrAway.AWAY)
                .stage(event.getMLBStage())
                .runs(calculateRuns(event, event.getTeam2().get()))
                .missedRuns(calculateRuns(event, event.getTeam1().get()))
                .winCoefficient(calculateOdd(event, Winner2.class))
                .build();

        firstTeamGame.setResult(calculateResult(firstTeamGame.getRuns().get(), firstTeamGame.getMissedRuns().get()));
        secondTeamGame.setResult(calculateResult(secondTeamGame.getRuns().get(), secondTeamGame.getMissedRuns().get()));

        teamGames.add(firstTeamGame);
        teamGames.add(secondTeamGame);

        return teamGames;
    }

    public Integer calculateRuns(MLBEvent event, Team team){
        Integer result = 0;
        List<Run> runs = event.getRuns().get();
        Iterator<Run> iterator = runs.iterator();
        Team runTeam = null;
        while (iterator.hasNext()){
            runTeam = iterator.next().getTeam().get();
            if(team.equals(runTeam))
                result++;
        }

        return result;
    }

    public Double calculateOdd(MLBEvent event, Class<? extends Odd> kind){
        Double result = 0.0;
        Iterator<Odd> iterator = event.getOdds().get().iterator();
        Odd odd = null;

        while (iterator.hasNext()){
            odd = iterator.next();
            if(odd.getClass().equals(kind))
                result = odd.getValue().get();
        }

        return result;
    }

    public EventResult calculateResult(Integer runs, Integer missedRuns){
        if(runs > missedRuns)
            return EventResult.WIN;
        else if (runs < missedRuns)
            return EventResult.LOSE;
        else
            return EventResult.DRAW;
    }

}
