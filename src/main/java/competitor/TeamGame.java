package competitor;

import competitor.state.HomeOrAway;
import competitor.state.WinOrLose;
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
@NoArgsConstructor
@AllArgsConstructor
public class TeamGame extends Team {

    @Builder(builderMethodName = "teamGameBuilder")
    public TeamGame(Long id, String name, Team opponent,
                    Timestamp timestamp, Long teamGameId,
                    List<Run> runs, List<Run> missedRuns,
                    Odd coefficientOfWin, WinOrLose result,
                    HomeOrAway place) {
        super(id, name);
        this.opponent = opponent;
        this.timestamp = timestamp;
        this.teamGameId = teamGameId;
        this.runs = runs;
        this.missedRuns = missedRuns;
        this.coefficientOfWin = coefficientOfWin;
        this.place = place;
        this.result = result;
    }

    private Team opponent;
    private Timestamp timestamp;
    private Long teamGameId;
    private List<Run> runs;
    private List<Run> missedRuns;
    private Odd coefficientOfWin;
    private HomeOrAway place;
    private WinOrLose result;

    @Override
    public String toString() {

        StringBuilder result = new StringBuilder();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

        result.append(getName()).append("\t")
                .append(getPlace()).append("\t")
                .append(opponent.toString()).append("\t")
                .append(sdf.format(timestamp)).append("\t")
                .append(getRuns().size()).append("\t")
                .append(getMissedRuns().size()).append("\t")
                .append(coefficientOfWin.toString()).append("\t")
                .append(this.result.toString());

        return result.toString();
    }
}
