package competitor;

import competitor.indicator.HomeOrAway;
import competitor.indicator.WinOrLose;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import odd.Odd;
import score.Run;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamGame extends Team {

    @Builder(builderMethodName = "teamGameBuilder")
    public TeamGame(Long id, String name, Team opponent,
                    LocalDate localDate, Long teamGameId,
                    List<Run> runs, List<Run> missedRuns,
                    Odd coefficientOfWin, WinOrLose result,
                    HomeOrAway place) {
        super(id, name);
        this.opponent = opponent;
        this.localDate = localDate;
        this.teamGameId = teamGameId;
        this.runs = runs;
        this.missedRuns = missedRuns;
        this.coefficientOfWin = coefficientOfWin;
        this.place = place;
        this.result = result;
    }

    private Team opponent;
    private LocalDate localDate;
    private Long teamGameId;
    private List<Run> runs;
    private List<Run> missedRuns;
    private Odd coefficientOfWin;
    private HomeOrAway place;
    private WinOrLose result;

    @Override
    public String toString() {

        StringBuilder result = new StringBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        result.append(getName()).append("\t")
                .append(getPlace()).append("\t")
                .append(opponent.toString()).append("\t")
                .append(localDate.format(formatter)).append("\t")
                .append(getRuns().size()).append("\t")
                .append(getMissedRuns().size()).append("\t")
                .append(coefficientOfWin.toString()).append("\t")
                .append(this.result.toString());

        return result.toString();
    }
}
