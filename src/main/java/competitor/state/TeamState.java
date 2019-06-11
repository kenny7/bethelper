package competitor.state;

import competitor.Team;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import competitor.state.HomeOrAway;
import lombok.experimental.SuperBuilder;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamState extends Team {

    @Builder(builderMethodName = "teamStateBuilder")
    public TeamState(Long id, String name, Long stateId,
                     Timestamp timestamp, Integer runs,
                     HomeOrAway place, Integer positionInLeague,
                     Integer gamesCount, Integer winsCount,
                     Integer losesCount, Double winPercent) {
        super(id, name);
        this.stateId = stateId;
        this.timestamp = timestamp;
        this.runs = runs;
        this.place = place;
        this.positionInLeague = positionInLeague;
        this.gamesCount = gamesCount;
        this.winsCount = winsCount;
        this.losesCount = losesCount;
        this.winPercent = winPercent;
    }

    private Long stateId;
    private Timestamp timestamp;
    private Integer runs;
    private HomeOrAway place;
    private Integer positionInLeague;
    private Integer gamesCount;
    private Integer winsCount;
    private Integer losesCount;
    private Double winPercent;

}
