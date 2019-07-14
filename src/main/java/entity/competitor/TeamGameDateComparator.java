package entity.competitor;

import java.util.Comparator;

public class TeamGameDateComparator implements Comparator<OldTeamGame> {

    @Override
    public int compare(OldTeamGame oldTeamGame1, OldTeamGame oldTeamGame2) {
        return oldTeamGame1.getLocalDate().compareTo(oldTeamGame2.getLocalDate());
    }
}
