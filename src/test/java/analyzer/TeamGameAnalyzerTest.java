package analyzer;

import analyzer.inputData.TeamGameInput;
import analyzer.outputData.TeamStateOutput;
import competitor.Team;
import competitor.TeamState;
import org.junit.Test;
import repositories.TeamGameRepository;

import java.time.LocalDate;
import java.util.LinkedList;

import static org.junit.Assert.*;

public class TeamGameAnalyzerTest {

    TeamGameAnalyzer analyzer;

    @Test
    public void createTeamStateForDate() {

    }

    @Test
    public void calculateCountOfGames() {

        analyzer = TeamGameAnalyzer.builder()
                .input(TeamGameInput.builder()
                        .teamGames(new LinkedList<>())
                        .repository(TeamGameRepository.builder()
                                .teamGames(new LinkedList<>())
                                .file("C:\\Users\\rock\\Documents\\BaseballTeamGames2019.txt")
                                .build())
                        .build())
                .outputData(TeamStateOutput.builder().build())
                .build();

        Team team = Team.builder()
                .name("Arizona Diamondbacks")
                .build();
        LocalDate localDate = LocalDate.of(2019, 06, 10);

        TeamState teamState = analyzer.createTeamStateForDate(team, localDate);

        System.out.println(teamState);

    }
}