package analyzer.teamStateAnalyzer;

import entity.competitor.TeamGame;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
public class ParameterCalculator {

    public List<TeamGame> calculate(WayCalculation wayCalculation){
        return wayCalculation.calculated();
    }

}
