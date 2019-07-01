package analyzer;

import analyzer.repository.MLBEventRepository;
import analyzer.repository.TeamRepository;
import entity.competitor.Team;
import entity.event.MLBEvent;
import entity.score.Run;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import analyzer.repository.TeamGameRepository;

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

    public List<MLBEvent> addMLBEvents(List<MLBEvent> events){
        List<MLBEvent> result = new LinkedList<>();

        for(MLBEvent event : events){
            addMLBEvent(event);
            result.add(event);
        }

        return result;
    }

    public MLBEvent addMLBEvent(MLBEvent event){

        //todo for test
        System.out.println(event);

        Team team = selectTeamByNameWithExistingCheck(event.getTeam1().get().getName().get());
        event.setTeam1(team);
        for(Run run : event.getRuns().get()){
            if(event.getTeam1().get().getName().get().equals(team.getName().get())){
                run.setTeam(team);
            }
        }

        team = selectTeamByNameWithExistingCheck(event.getTeam2().get().getName().get());
        event.setTeam2(team);
        for(Run run : event.getRuns().get()){
            if(event.getTeam2().get().getName().get().equals(team.getName().get())){
                run.setTeam(team);
            }
        }

        mlbEventRepository.add(event);

        return event;
    }

    public Team selectTeamByNameWithExistingCheck(String name){
        Team team = selectTeamByName(name);

        if(team == null){
            team = teamRepository.add(new Team(name));
            System.out.println("in database was added team with name " + team.getName().get());
        }

        return team;
    }

    public Team selectTeamByName(String name){
        Team team = null;
        try {
            team = teamRepository.selectTeamByName(name);
        } catch (javax.persistence.NoResultException e){
            System.out.println("there is not team with this name in database");
        }
        return team;
    }

    public void updateMLBEventList(List<MLBEvent> events){

        for (MLBEvent event : events)
            updateMLBEvent(event);
    }

    public void updateMLBEvent(MLBEvent event){

        mlbEventRepository.update(event);
        System.out.println("update " + event);
    }
}
