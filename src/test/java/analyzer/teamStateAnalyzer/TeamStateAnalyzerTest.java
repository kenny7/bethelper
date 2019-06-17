package analyzer.teamStateAnalyzer;

import analyzer.parser.ParserTeamGameFromString;
import analyzer.parser.ParserTeamStateFromString;
import analyzer.dao.FileTeamGameDAO;
import analyzer.dao.FileTeamStateDAO;
import entity.competitor.Team;
import entity.competitor.TeamGameWithTeamState;
import org.junit.Test;
import printer.Printer;
import printer.TeamGameWithTeamStatePrinter;
import repositories.TeamGameTextFileRepository;
import repositories.TeamStateRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TeamStateAnalyzerTest {

    ParserTeamGameFromString parserTeamGameFromString = ParserTeamGameFromString.builder()
            .regex("\\t")
            .build();
    FileTeamGameDAO fileTeamGameDAO = FileTeamGameDAO.builder()
            .file("C:\\Users\\rock\\Documents\\TeamGame2019.txt")
            .parser(parserTeamGameFromString)
            .build();
    TeamGameTextFileRepository teamGameTextFileRepository = TeamGameTextFileRepository.builder()
            .teamGameDAO(fileTeamGameDAO)
            .build();

    ParserTeamStateFromString parserTeamStateFromString = ParserTeamStateFromString.builder()
            .regex("(\\w+\\.*[\\s*\\w+]*)\\t" +
                    "(\\d{2}\\.{1}\\d{2}\\.{1}\\d{4})\\t" +
                    "(\\d*)\\t" +
                    "(\\d*)\\t" +
                    "(\\d*,{1}\\d*)")
            .build();
    FileTeamStateDAO fileTeamStateDAO = FileTeamStateDAO.builder()
            .file("C:\\Users\\rock\\Documents\\TeamState2019.txt")
            .parser(parserTeamStateFromString)
            .build();
    TeamStateRepository teamStateRepository = TeamStateRepository.builder()
            .teamStateDAO(fileTeamStateDAO)
            .build();

    TeamStateAnalyzer analyzer = TeamStateAnalyzer.builder()
            .teamGameTextFileRepository(teamGameTextFileRepository)
            .teamStateRepository(teamStateRepository)
            .build();

    @Test
    public void teamGameWithTeamStates() {
    }

    @Test
    public void createTeamGameWithTeamState() {

        teamGameTextFileRepository.loadDataToRepository(null);
        teamStateRepository.loadDataToRepository(null);

        Team team = Team.builder()
                .name("Arizona Diamondbacks")
                .build();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate localDate = LocalDate.parse("01.05.2017", formatter);

        //TeamGameWithTeamState teamGameWithTeamState = analyzer.createTeamGameWithTeamState(team, localDate);

        analyzer.setTeamGames(teamGameTextFileRepository.selectByName("Arizona Diamondbacks"));
        List<TeamGameWithTeamState> states = analyzer.teamGameWithTeamStates();


        Printer printer = TeamGameWithTeamStatePrinter.builder()
                .teamGameWithTeamStates(states)
                .build();

        printer.printData();

        //System.out.println(((TeamGameWithTeamStatePrinter) printer).teamGameWithTeamStateToString(teamGameWithTeamState));

    }
}