package competitor;

import competitor.indicator.HomeOrAway;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamState extends Team {

    @Builder(builderMethodName = "teamStateBuilder")
    public TeamState(Long id, String name, Long stateId,
                     LocalDate localDate, Integer runs,
                     Integer positionInLeague, Integer gamesCount,
                     Integer winsCount, Integer losesCount, Double winPercent) {
        super(id, name);
        this.stateId = stateId;
        this.localDate = localDate;
        this.runs = runs;
        this.positionInLeague = positionInLeague;
        this.gamesCount = gamesCount;
        this.winsCount = winsCount;
        this.losesCount = losesCount;
        this.winPercent = winPercent;
    }

    private Long stateId;
    private LocalDate localDate;
    private Integer gamesCount;
    private Integer winsCount;
    private Integer losesCount;
    private Double winPercent;
    private Integer runs;
    private Integer positionInLeague;

}
