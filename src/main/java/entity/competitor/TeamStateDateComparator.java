package entity.competitor;

import java.util.Comparator;

public class TeamStateDateComparator implements Comparator<TeamState> {

    @Override
    public int compare(TeamState t1, TeamState t2) {

        return t1.getLocalDate().compareTo(t2.getLocalDate());
    }
}
