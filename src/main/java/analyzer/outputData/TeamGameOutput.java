package analyzer.outputData;

import competitor.TeamGame;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamGameOutput implements OutputData {

    private List<TeamGame> teamGames;

    @Override
    public void submitDataOutput() {
        for (TeamGame teamGame : teamGames)
            System.out.println(teamGame);
    }

    @Override
    public <T> void setOutputData(T t) {
        if(t.getClass() == TeamGame.class) {
            teamGames.add((TeamGame) t);
        }
    }

    @Override
    public List<TeamGame> getOutputData() {
        return teamGames;
    }
}
