package analyzer.teamStateAnalyzer;

import entity.competitor.TeamGame;
import entity.competitor.TeamGameDateComparator;
import entity.competitor.teamGame.EventResult;
import entity.competitor.indicator.WinPercent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MyFirstWay implements WayCalculation {

    private List<TeamGame> teamGames;

    @Override
    public List<TeamGame> calculated() {
        return calculateWinPercent(teamGames);
    }

    public List<TeamGame> calculateWinPercent(List<TeamGame> teamGames){

        Collections.sort(teamGames, new TeamGameDateComparator());

        List<TeamGame> result = new LinkedList<>();

        Integer gamesCount = 0, winsCount = 0;
        WinPercent winPersent = new WinPercent(new Double(0));
        TeamGame temp = null;

        for(int i = 0; i < teamGames.size(); i++){

            temp = teamGames.get(i);
            winPersent = new WinPercent(winsCount, gamesCount);

            gamesCount++;
            if(temp.getResult() == EventResult.WIN)
                winsCount++;

            temp.setWinPercent(winPersent);
            result.add(temp);
        }

        return result;
    }
}
