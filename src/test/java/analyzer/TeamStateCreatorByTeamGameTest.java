package analyzer;

import analyzer.dao.FileTeamGameDAO;
import analyzer.teamStateAnalyzer.TeamStateCreatorByTeamGame;
import entity.competitor.TeamGame;
import entity.competitor.TeamGameDateComparator;
import entity.competitor.TeamState;
import entity.competitor.TeamStateDateComparator;
import org.junit.Test;
import printer.Printer;
import printer.TeamStatePrinter;
import repositories.TeamGameRepository;

import java.util.Collections;
import java.util.List;

public class TeamStateCreatorByTeamGameTest {

    ParserTeamGameFromString parser = ParserTeamGameFromString.builder()
            //.regex("\t")
            .build();

    FileTeamGameDAO teamGameDAO = FileTeamGameDAO.builder()
            .file("C:\\Users\\rock\\Documents\\TeamGame2017.txt")
            .parser(ParserTeamGameFromString.builder().build())
            .build();

    TeamGameRepository repository = TeamGameRepository.builder()
            .teamGameDAO(teamGameDAO)
            .build();

    TeamStateCreatorByTeamGame creator = TeamStateCreatorByTeamGame.builder()
            .repository(repository)
            .build();




    @Test
    public void calculateTeamStatesForAllTeamGames() {

        repository.loadDataToRepository(null);

        List<TeamGame> teamGames = repository.selectAll();

        TeamGameDateComparator teamGameDateComparator = new TeamGameDateComparator();
        Collections.sort(teamGames, teamGameDateComparator);

        creator.setTeamGames(teamGames);

        TeamStateDateComparator dateComparator = new TeamStateDateComparator();
        List<TeamState> teamStates = creator.calculateTeamStatesForAllTeamGames();

        Collections.sort(teamStates, dateComparator);

        Printer printer = TeamStatePrinter.builder().build();
        ((TeamStatePrinter) printer).setTeamStates(teamStates);

        printer.printData();
    }

    @Test
    public void createTeamStateForDate() {
    }
}