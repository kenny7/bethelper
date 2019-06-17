package entity.competitor.indicator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WinPercent implements Parameter{

    private Double value;

    public WinPercent(Integer winCount, Integer gamesCount) {
        if(gamesCount != 0)
            value = new Double(winCount)/new Double(gamesCount);
        else
            value = new Double(0);
    }
}
