package analyzer;

import analyzer.repository.MLBEventRepository;
import analyzer.repository.RunRepository;
import analyzer.repository.TeamRepository;
import entity.competitor.Team;
import entity.competitor.teamGame.EventResult;
import entity.competitor.teamGame.TeamGame;
import entity.event.MLBEvent;
import entity.score.Run;
import lombok.*;
import analyzer.repository.TeamGameRepository;

import javax.persistence.NoResultException;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DatabaseAdministrator {

    private TeamGameRepository teamGameRepository;
    private MLBEventRepository mlbEventRepository;
    private TeamRepository teamRepository;
    private RunRepository runRepository;

    public Team addTeam(Team team){
        return teamRepository.add(team);
    }

    public List<MLBEvent> addMLBEvents(List<MLBEvent> events){
        List<MLBEvent> result = new LinkedList<>();

        for(MLBEvent event : events){
            addMLBEvent(event);
            result.add(event);
        }

        return result;
    }

    public MLBEvent addMLBEvent(MLBEvent event){

        Team team1 = selectTeamByNameWithExistingCheck(event.getTeam1().get().getName().get());
        event.setTeam1(team1);

        Team team2 = selectTeamByNameWithExistingCheck(event.getTeam2().get().getName().get());
        event.setTeam2(team2);

        List<Run> runs = new LinkedList<>();
        for (Run run : event.getRuns().get()){
            if(run.getTeam().get().equals(team1)){
                runs.add(addRun(new Run(team1)));
            }
            if(run.getTeam().get().equals(team2)){
                runs.add(addRun(new Run(team2)));
            }
        }
        event.setRuns(runs);

        mlbEventRepository.add(event);
        return event;
    }

    public MLBEvent selectMLBEventById(Long id){
        return mlbEventRepository.get(id);
    }

    public List<MLBEvent> selectAllMLBEvents(){
        return mlbEventRepository.getAll();
    }

    public List<MLBEvent> selectMLBEventsBetweenDate(
            LocalDateTime startDate, LocalDateTime finishDate){
        return mlbEventRepository.selectMLBEventBetweenDate(startDate, finishDate);
    }

    public void updateMLBEventList(List<MLBEvent> events){

        for (MLBEvent event : events)
            updateMLBEvent(event);
    }

    public void updateMLBEvent(MLBEvent event){

        mlbEventRepository.update(event);
        System.out.println("update " + event);
    }

    public Team selectTeamByNameWithExistingCheck(@NonNull String name){
        try{
            return selectTeamByName(name);
        } catch (NoResultException e){
            System.out.println("in database was added team with name " + name);
            return addTeam(new Team(name));
        }
    }

    public Team selectTeamByName(String name){
        try {
            return teamRepository.selectTeamByName(name);
        } catch (javax.persistence.NoResultException e){
            System.out.println("there is not team with this name in database");
            throw e;
        }
    }

    public List<TeamGame> addTeamGames(List<TeamGame> teamGames){

        List<TeamGame> result = new LinkedList<>();

        for(TeamGame teamGame : teamGames){
            TeamGame temp = teamGameRepository.add(teamGame);
            result.add(temp);
            System.out.println("this teamGame was added in database " + temp);
        }

        return result;
    }

    public TeamGame selectTeamGameById(Long id){
        return teamGameRepository.get(id);
    }

    public List<TeamGame> selectTeamGamesBetweenDate(
            LocalDateTime startDate, LocalDateTime finishDate){
        return teamGameRepository.selectTeamGamesBetweenDate(startDate, finishDate);
    }

    public void updateTeamGameList(List<TeamGame> teamGames){
        for(TeamGame teamGame : teamGames){
            updateTeamGame(teamGame);
        }
    }

    public void updateTeamGame(TeamGame teamGame){
        teamGameRepository.update(teamGame);
    }

    public List<TeamGame> selectTeamGamesBetweenDatesByTeamId
            (Long id, LocalDateTime startDate, LocalDateTime finishDate) {
        return teamGameRepository.selectTeamGamesBetweenDatesByTeamId(id, startDate, finishDate);
    }

    public List<TeamGame> selectTeamGamesBetweenDatesByTeam
            (Team team, LocalDateTime startDate, LocalDateTime finishDate){
        return teamGameRepository
                .selectTeamGamesBetweenDatesByTeam(team, startDate, finishDate);
    }

    public List<TeamGame> selectTeamGamesBetweenDatesByIdAndResult
            (Long id, EventResult result, LocalDateTime startDate, LocalDateTime finishDate) {
        return teamGameRepository.selectTeamGamesBetweenDatesByIdAndResult(id, result, startDate, finishDate);
    }

    public List<TeamGame> selectTeamGameBetweenDatesByTeamAndResult
            (Team team, EventResult result, LocalDateTime startDate, LocalDateTime finishDate){

        return teamGameRepository
                .selectTeamGamesBetweenDatesByTeamAndResult(team, result, startDate, finishDate);

    }

    public Run addRun(Run run) {
        return runRepository.add(run);
    }

    public static void main(String[] args) {

    }
}
