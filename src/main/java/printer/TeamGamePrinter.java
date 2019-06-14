package printer;

import entity.competitor.TeamGame;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeamGamePrinter implements Printer{

    private List<TeamGame> teamGames;

    @Override
    public void printData() {
        for (TeamGame teamGame : teamGames)
            System.out.println(teamGame);
    }
}
