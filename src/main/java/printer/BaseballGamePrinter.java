package printer;

import entity.event.BaseballGame;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BaseballGamePrinter implements Printer {

    private List<BaseballGame> baseballGames;

    @Override
    public void printData() {
        for(BaseballGame baseballGame : baseballGames)
            System.out.println(baseballGame);
    }

}
