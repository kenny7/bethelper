package analyzer.inputData;

import competitor.TeamGame;
import event.BaseballGame;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BaseballGameInput implements InputData{

    private List<BaseballGame> baseballGames;

    @Override
    public List<BaseballGame> getInputData() {
        return baseballGames;
    }

    @Override
    public <T> void setInputData(T t) {
        if(t.getClass() == BaseballGame.class) {
            baseballGames.add((BaseballGame) t);
        }
    }
}
