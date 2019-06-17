package repositories;

import analyzer.dao.FileTeamStateDAO;
import entity.competitor.Team;
import entity.competitor.TeamState;
import entity.competitor.TeamStateDateComparator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import repositories.filter.Filter;

import java.time.LocalDate;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeamStateRepository {

    private FileTeamStateDAO teamStateDAO;
    private List<TeamState> teamStates;

    public void loadDataToRepository(Filter filter){
        teamStates = teamStateDAO.selectByFilter(filter);
    }

    public TeamState selectTeamStateByNameAndDate(Team team, LocalDate date){

        List<TeamState> teamStates = selectBeforeDateIncluding(selectTeamStateByName(team), date);
        Collections.sort(teamStates, new TeamStateDateComparator());



        return (teamStates.size() != 0) ? teamStates.get(teamStates.size()-1) : new TeamState();
    }

    public List<TeamState> selectBeforeDateNotIncluding(List<TeamState> teamStates, LocalDate localDate) {

        List<TeamState> selectedTeamStates = new LinkedList<>();

        for(TeamState teamState : teamStates){
            if(compareDateLessThanDateNotIncluding(teamState.getLocalDate(), localDate)){
                selectedTeamStates.add(teamState);
            }
        }

        return selectedTeamStates;
    }

    private boolean compareDateLessThanDateNotIncluding(LocalDate comparedDate, LocalDate lessThanDate){

        if(comparedDate.getYear() <= lessThanDate.getYear()){
            if(comparedDate.getDayOfYear() < lessThanDate.getDayOfYear())
                return true;
            else
                return false;
        } else
            return false;
    }

    public List<TeamState> selectBeforeDateIncluding(List<TeamState> teamStates, LocalDate localDate) {

        List<TeamState> selectedTeamGames = new LinkedList<>();

        for(TeamState teamState : teamStates){
            if(compareDateLessThanDateIncluding(teamState.getLocalDate(), localDate)){
                selectedTeamGames.add(teamState);
            }
        }

        return selectedTeamGames;
    }

    private boolean compareDateLessThanDateIncluding(LocalDate comparedDate, LocalDate lessThanDate){

        if(comparedDate.getYear() <= lessThanDate.getYear()){
            if(comparedDate.getDayOfYear() <= lessThanDate.getDayOfYear())
                return true;
            else
                return false;
        } else
            return false;
    }

    public List<TeamState> selectTeamStateByName(Team team){

        List<TeamState> result = new LinkedList<>();

        for(TeamState teamState : selectAll()){
            if(teamState.getName().equals(team.getName()))
                result.add(teamState);
        }

        return result;
    }

    public List<TeamState> selectAll(){
        return teamStates;
    }
}
