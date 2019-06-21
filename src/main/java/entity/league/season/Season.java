package entity.league.season;

import entity.event.MLBEvent;
import entity.league.TeamLeagueState;
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
    private List<MLBEvent> games;
    private List<TeamLeagueState> americanLeague;
    private List<TeamLeagueState> nationalLeague;

}
