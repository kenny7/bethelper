package analyzer;

import analyzer.inputData.TeamGameInput;
import analyzer.outputData.OutputData;
import competitor.Team;
import competitor.TeamGame;
import competitor.TeamState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeamGameAnalyzer {

    private TeamGameInput input;
    private List<TeamState> teamStates;
    private OutputData outputData;

    public TeamState createTeamStateForDate(Team team, LocalDate date){

        TeamState teamState = TeamState.teamStateBuilder()
                .name(team.getName())
                .localDate(date)
                .build();

        teamState.setGamesCount(calculateCountOfGames(date));

        return teamState;

    }

    public Integer calculateCountOfGames(LocalDate date){

        List<TeamGame> teamGames = input.getRepository().selectBeforeDateNotIncluding(date);

        int count = teamGames.size();

        return count;
    }

    public static void main(String[] args) {


    }
}
