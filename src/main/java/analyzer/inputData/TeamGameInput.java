package analyzer.inputData;

import competitor.TeamGame;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import repositories.TeamGameRepository;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeamGameInput implements InputData{

    private List<TeamGame> teamGames;
    private TeamGameRepository repository;

    @Override
    public List<TeamGame> getInputData() {
        return teamGames;
    }

    @Override
    public <T> void setInputData(T t) {
        if(t.getClass() == TeamGame.class) {
            teamGames.add((TeamGame) t);
        }
    }


}
