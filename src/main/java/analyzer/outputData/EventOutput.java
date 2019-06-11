package analyzer.outputData;

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
public class EventOutput implements OutputData{

    private List<BaseballGame> baseballGames;

    public void submitDataOutput(){
        System.out.println(baseballGames);
    }

    @Override
    public <T> void setOutputData(T t) {
        if(t.getClass() == BaseballGame.class) {
            baseballGames.add((BaseballGame) t);
        }
    }

    @Override
    public List<BaseballGame> getOutputData() {
        return baseballGames;
    }
}
