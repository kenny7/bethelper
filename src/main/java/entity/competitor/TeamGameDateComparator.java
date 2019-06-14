package entity.competitor;

import java.util.Comparator;

public class TeamGameDateComparator implements Comparator<TeamGame> {

    @Override
    public int compare(TeamGame teamGame1, TeamGame teamGame2) {
        return teamGame1.getLocalDate().compareTo(teamGame2.getLocalDate());
    }
}
