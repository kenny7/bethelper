package analyzer;

import analyzer.inputData.TeamGameInput;
import competitor.TeamGame;
import competitor.state.TeamState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeamGameAnalyzer {

    private TeamGameInput input;
    private List<TeamState> teamStates;

    public void createTeamState(TeamGame teamGame){

        /*TeamState teamState = TeamState.teamStateBuilder()
                .name(teamGame.getName())
                .localDate(teamGame.getLocalDate())
                .
                .build();*/
    }
}
