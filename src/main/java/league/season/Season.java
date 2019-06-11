package league.season;

import event.BaseballGame;
import league.TeamLeagueState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Season {

    private Long id;
    private List<BaseballGame> games;
    private List<TeamLeagueState> americanLeague;
    private List<TeamLeagueState> nationalLeague;

}
