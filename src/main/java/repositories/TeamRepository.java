package repositories;

import entity.competitor.Team;

import java.util.LinkedList;
import java.util.List;


public class TeamRepository {

    private static List<Team> teams = new LinkedList<>();

    public void addTeam(Team team){
        if(!containsTeam(team))
            teams.add(team);
    }

    public boolean containsTeam(Team team){
        if(teams.contains(team))
            return true;
        else
            return false;
    }
}
