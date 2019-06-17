package entity.event;

import entity.competitor.Team;
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
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BaseballGame {

    private Long id;
    private Team team1;
    private Team team2;
    private List<Run> firstTeamRun;
    private List<Run> secondTeamRun;
    private Odd coefficientOfWin1;
    private Odd coefficientOfWin2;
    private LocalDate localDate;

    @Override
    public String toString() {

        StringBuilder result = new StringBuilder();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        result.append(getId()).append("\t")
                .append(team1.toString()).append(" - ").append(team2.toString()).append("\t")
                .append(firstTeamRun.size()).append(":").append(secondTeamRun.size()).append("\t")
                .append(coefficientOfWin1.toString()).append("\t")
                .append(coefficientOfWin2.toString()).append("\t")
                .append(getLocalDate().format(formatter));

        return result.toString();
    }
}
