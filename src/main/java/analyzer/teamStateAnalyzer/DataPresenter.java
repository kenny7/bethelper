package analyzer.teamStateAnalyzer;

import entity.competitor.TeamGame;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import repositories.TeamGameTextFileRepository;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DataPresenter {

    private List<TeamGame> teamGames;
    private TeamGameTextFileRepository teamGameRepository;

    public void presentDate(){

        for(TeamGame teamGame : teamGames){

            StringBuilder result = new StringBuilder(teamGame.toString());
            result.append("\t");

            TeamGame opponent = teamGameRepository
                    .selectTeamGameByEventIdAndName(teamGame.getEventId(), teamGame.getOpponent().getName());

            String winPercent =
                    String.format("%,.3f", opponent.getWinPercent().getValue());

            result.append(winPercent);

            System.out.println(result.toString());
        }

    }

}
