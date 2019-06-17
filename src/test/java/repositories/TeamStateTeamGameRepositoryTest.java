package repositories;

import analyzer.parser.ParserTeamStateFromString;
import analyzer.dao.FileTeamStateDAO;
import entity.competitor.Team;
import org.junit.Test;
import printer.Printer;
import printer.TeamStatePrinter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TeamStateTeamGameRepositoryTest {

    ParserTeamStateFromString parser = ParserTeamStateFromString.builder()
            .regex("(\\w+\\.*[\\s*\\w+]*)\\t" +
                    "(\\d{2}\\.{1}\\d{2}\\.{1}\\d{4})\\t" +
                    "(\\d*)\\t" +
                    "(\\d*)\\t" +
                    "(\\d*,{1}\\d*)")
            .build();

    FileTeamStateDAO dao = FileTeamStateDAO.builder()
            .file("C:\\Users\\rock\\Documents\\TeamState2018.txt")
            .parser(parser)
            .build();

    TeamStateRepository repository = TeamStateRepository.builder()
            .teamStateDAO(dao)
            .build();

    @Test
    public void selectTeamStateByNameAndDate() {

        repository.loadDataToRepository(null);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate localDate = LocalDate.parse("01.05.2017", formatter);

        System.out.println(repository.selectTeamStateByNameAndDate(Team.builder()
                .name("Arizona Diamondbacks")
                .build(), localDate));

    }

    @Test
    public void selectTeamStateByName() {
    }

    @Test
    public void selectAll() {

        repository.loadDataToRepository(null);

        Printer printer = TeamStatePrinter.builder()
                .teamStates(repository.selectAll())
                .build();

        printer.printData();
    }
}