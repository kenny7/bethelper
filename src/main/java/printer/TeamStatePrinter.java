package printer;

import entity.competitor.TeamState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeamStatePrinter implements Printer{

    private List<TeamState> teamStates = new LinkedList<>();

    @Override
    public void printData() {
        for(TeamState teamState : teamStates)
            System.out.println(teamStateToString(teamState));
    }

    private String teamStateToString(TeamState teamState){
        StringBuilder result = new StringBuilder(teamState.getName()).append("\t");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        result.append(teamState.getLocalDate().format(formatter)).append("\t")
                .append(teamState.getGamesCount()).append("\t")
                .append(teamState.getWinsCount()).append("\t");
        String winPercent = String.format("%,.3f", teamState.getWinPercent());
        result.append(winPercent);

        return result.toString();
    }
}
