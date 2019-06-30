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

        Team team = teamRepository.selectTeamByName(event.getTeam1().get().getName().get());
        event.setTeam1(team);
        for(Run run : event.getRuns().get()){
            if(event.getTeam1().get().getName().get().equals(team.getName().get())){
                run.setTeam(team);
            }
        }

        team = teamRepository.selectTeamByName(event.getTeam2().get().getName().get());
        event.setTeam2(team);
        for(Run run : event.getRuns().get()){
            if(event.getTeam2().get().getName().get().equals(team.getName().get())){
                run.setTeam(team);
            }
        }

        mlbEventRepository.add(event);

        return event;
    }

    public Team addTeam(Team team){

        try {
            team = teamRepository.selectTeamByName(team.getName().get());
        } catch (javax.persistence.NoResultException e){

        }

        return null;
    }
}
