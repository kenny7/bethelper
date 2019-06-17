package entity.competitor;

import entity.competitor.indicator.HomeOrAway;
import entity.competitor.indicator.WinOrLose;
import entity.competitor.indicator.WinPercent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import entity.odd.Odd;
import entity.score.Run;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamGame extends Team {

    @Builder(builderMethodName = "teamGameBuilder")
    public TeamGame(Long id, String name, Team opponent,
                    LocalDate localDate, Long eventId,
                    List<Run> runs, List<Run> missedRuns,
                    Odd coefficientOfWin, WinOrLose result,
                    HomeOrAway place, WinPercent winPercent) {
        super(id, name);
        this.opponent = opponent;
        this.localDate = localDate;
        this.eventId = eventId;
        this.runs = runs;
        this.missedRuns = missedRuns;
        this.coefficientOfWin = coefficientOfWin;
        this.place = place;
        this.result = result;
        this.winPercent = winPercent;
    }

    private Team opponent;
    private LocalDate localDate;
    private Long eventId;
    private List<Run> runs;
    private List<Run> missedRuns;
    private Odd coefficientOfWin;
    private HomeOrAway place;
    private WinOrLose result;
    private WinPercent winPercent;

    @Override
    public String toString() {

        StringBuilder result = new StringBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        result.append(getEventId()).append("\t")
                .append(getName()).append("\t")
                .append(getPlace()).append("\t")
                .append(opponent.toString()).append("\t")
                .append(localDate.format(formatter)).append("\t")
                .append(getRuns().size()).append("\t")
                .append(getMissedRuns().size()).append("\t")
                .append(coefficientOfWin.toString()).append("\t")
                .append(this.result.toString()).append("\t");
        String winPercent = String.format("%,.3f", getWinPercent().getValue());
        result.append(winPercent);

        return result.toString();
    }
}
