package printer;

import entity.competitor.TeamGameWithTeamState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamGameWithTeamStatePrinter implements Printer{

    private List<TeamGameWithTeamState> teamGameWithTeamStates;

    @Override
    public void printData() {
        for(TeamGameWithTeamState t : teamGameWithTeamStates)
            System.out.println(teamGameWithTeamStateToString(t));
    }

    public String teamGameWithTeamStateToString(TeamGameWithTeamState game){

        StringBuilder result = new StringBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        result.append(game.getName()).append("\t")
                .append(game.getPlace()).append("\t")
                .append(game.getOpponent().toString()).append("\t")
                .append(game.getLocalDate().format(formatter)).append("\t")
                //.append(game.getRuns().size()).append("\t")
                //.append(game.getMissedRuns().size()).append("\t")
                .append(game.getCoefficientOfWin().toString()).append("\t")
                .append(game.getResult().toString()).append("\t");

        String winPercent = String.format("%,.3f", game.getTeamState().getWinPercent());
        result.append(winPercent).append("\t");
        winPercent = String.format("%,.3f", game.getOpponentState().getWinPercent());
        result.append(winPercent);

        return result.toString();
    }
}
