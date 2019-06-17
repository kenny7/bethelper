package repositories;

import analyzer.dao.TeamGameDAO;
import analyzer.repository.TeamGameRepository;
import entity.competitor.Team;
import entity.competitor.TeamGame;
import entity.competitor.TeamGameDateComparator;
import entity.competitor.indicator.WinOrLose;
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
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamGameTextFileRepository implements TeamGameRepository {

    private TeamGameDAO teamGameDAO;
    private List<TeamGame> teamGames;

    @Override
    public TeamGame selectTeamGameById(Long id) {
        return null;
    }

    @Override
    public List<TeamGame> selectTeamGamesByName(String name) {
        return null;
    }

    @Override
    public List<TeamGame> selectTeamGamesByBaseballGameId(Long id) {
        return null;
    }

    @Override
    public void writeTeamGame(TeamGame teamGame) {

    }

    @Override
    public void update(TeamGame teamGame) {

    }

    @Override
    public void deleteById(Long id) {

    }

    public void loadDataToRepository(Filter filter){
        teamGames = teamGameDAO.selectByFilter(null);
    }

    public TeamGame selectTeamGameByEventIdAndName(Long id, String name){
        List<TeamGame> teamGames = selectByName(name);
        TeamGame result = new TeamGame();

        for(TeamGame teamGame : selectByEventId(teamGames, id)){
            result = teamGame;
        }

        return result;
    }

    public List<TeamGame> selectByEventId(List<TeamGame> teamGames, Long id){

        List<TeamGame> result = new LinkedList<>();

        for(TeamGame teamGame : teamGames){

            if(teamGame.getEventId() == id)
                result.add(teamGame);
        }

        return result;
    }

    public TeamGame selectTeamGameByNameAndDate(Team team, LocalDate date){

        List<TeamGame> teamGames = selectBeforeDateIncluding(selectByName(team.getName()), date);
        Collections.sort(teamGames, new TeamGameDateComparator());

        return teamGames.get(teamGames.size()-1);
    }

    public List<TeamGame> selectAll(){
        return teamGames;
    }

    public List<TeamGame> selectTeamGamesByNameAndDateSortedByDate(Team team, LocalDate date, boolean isDateIncluding){

        List<TeamGame> selectedTeamGamesByName = selectByName(team.getName());
        List<TeamGame> selectedTeamGamesByNameAndDateSortedByDate = new LinkedList<>();

        if(isDateIncluding){
            selectedTeamGamesByNameAndDateSortedByDate = selectBeforeDateIncluding(selectedTeamGamesByName, date);
        } else {
            selectedTeamGamesByNameAndDateSortedByDate = selectBeforeDateNotIncluding(selectedTeamGamesByName, date);
        }

        Collections.sort(selectedTeamGamesByNameAndDateSortedByDate, new TeamGameDateComparator());

        return selectedTeamGamesByNameAndDateSortedByDate;
    }

    public List<TeamGame> selectTeamGamesByNameAndDateAndResultSortedByDate(LocalDate date, Team team, WinOrLose result){

        List<TeamGame> selectedTeamGamesByNameAndDateAndResultSortedByDate = new LinkedList<>();

        selectedTeamGamesByNameAndDateAndResultSortedByDate =
                selectByResult(selectTeamGamesByNameAndDateSortedByDate(team, date, true), result);

        return selectedTeamGamesByNameAndDateAndResultSortedByDate;

    }

    public List<TeamGame> selectByResult(List<TeamGame> teamGames, WinOrLose result){

        List<TeamGame> selectedTeamGames = new LinkedList<>();

        for(TeamGame teamGame : teamGames){
            if(compareResult(teamGame.getResult(), result)){
                selectedTeamGames.add(teamGame);
            }
        }

        return selectedTeamGames;
    }

    private boolean compareResult(WinOrLose teamResult, WinOrLose comparedResult){
        if(teamResult == comparedResult)
            return true;
        else
            return false;
    }

    public List<TeamGame> selectByName(String comparedName){
        List<TeamGame> selectedTeamGames = new LinkedList<>();

        for(TeamGame teamGame : teamGames){
            if(compareTeamName(teamGame.getName(), comparedName)){
                selectedTeamGames.add(teamGame);
            }
        }

        return selectedTeamGames;
    }

    private boolean compareTeamName(String nameOfFirstTeam, String nameOfSecondTeam){
        if(nameOfFirstTeam.equals(nameOfSecondTeam))
            return true;
        else
            return false;
    }

    public List<TeamGame> selectBeforeDateNotIncluding(List<TeamGame> teamGames, LocalDate localDate) {

        List<TeamGame> selectedTeamGames = new LinkedList<>();

        for(TeamGame teamGame : teamGames){
            if(compareDateLessThanDateNotIncluding(teamGame.getLocalDate(), localDate)){
                selectedTeamGames.add(teamGame);
            }
        }

        return selectedTeamGames;
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

    public List<TeamGame> selectBeforeDateIncluding(List<TeamGame> teamGames, LocalDate localDate) {

        List<TeamGame> selectedTeamGames = new LinkedList<>();

        for(TeamGame teamGame : teamGames){
            if(compareDateLessThanDateIncluding(teamGame.getLocalDate(), localDate)){
                selectedTeamGames.add(teamGame);
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
}
