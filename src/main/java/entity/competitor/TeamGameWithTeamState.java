package entity.competitor;

import entity.competitor.indicator.HomeOrAway;
import entity.competitor.indicator.WinOrLose;
import entity.competitor.indicator.WinPercent;
import entity.odd.Odd;
import entity.score.Run;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamGameWithTeamState extends TeamGame{

    private TeamState opponentState;
    private TeamState teamState;

    @Builder(builderMethodName = "teamGameWithTeamStateBuilder")
    public TeamGameWithTeamState(Long id, String name, Team opponent,
                                 LocalDate localDate, Long eventId,
                                 List<Run> runs, List<Run> missedRuns,
                                 Odd coefficientOfWin, WinOrLose result,
                                 HomeOrAway place, WinPercent winPercent,
                                 TeamState opponentState, TeamState teamState) {
        super(id, name, opponent, localDate, eventId, runs, missedRuns, coefficientOfWin, result, place, winPercent);
        this.opponentState = opponentState;
        this.teamState = teamState;
    }

}
