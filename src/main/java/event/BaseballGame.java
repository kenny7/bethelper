package event;

import competitor.Team;
import competitor.state.TeamState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import odd.Odd;
import score.Run;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BaseballGame {

    private Team team1;
    private Team team2;
    private List<Run> firstTeamRun;
    private List<Run> secondTeamRun;
    private Odd coefficientOfWin1;
    private Odd coefficientOfWin2;
    private Timestamp gameTimestamp;

    @Override
    public String toString() {

        StringBuilder result = new StringBuilder();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.mm.yyyy");

        result.append(team1.toString()).append(" - ").append(team2.toString()).append("\t")
                .append(firstTeamRun.size()).append(":").append(secondTeamRun.size()).append("\t")
                .append(coefficientOfWin1.toString()).append("\t")
                .append(coefficientOfWin2.toString()).append("\t")
                .append(sdf.format(getGameTimestamp()));

        return result.toString();
    }
}
