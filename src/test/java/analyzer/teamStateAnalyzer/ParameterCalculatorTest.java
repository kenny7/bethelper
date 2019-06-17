package analyzer.teamStateAnalyzer;

import analyzer.parser.ParserTeamGameFromString;
import analyzer.dao.FileTeamGameDAO;
import entity.competitor.TeamGame;
import org.junit.Test;
import repositories.TeamGameTextFileRepository;

import java.util.LinkedList;
import java.util.List;

public class ParameterCalculatorTest {

    ParameterCalculator calculator = ParameterCalculator.builder().build();
    ParserTeamGameFromString parser = ParserTeamGameFromString.builder()
            .regex("\\t")
            .build();

    FileTeamGameDAO dao = FileTeamGameDAO.builder()
            .file("C:\\Users\\rock\\Documents\\TeamGame2018.txt")
            .parser(parser)
            .build();

    TeamGameTextFileRepository repository = TeamGameTextFileRepository.builder()
            .teamGameDAO(dao)
            .build();

    @Test
    public void calculate() {

        repository.loadDataToRepository(null);
        List<String> teams = new LinkedList<>();
        teams.add("Arizona Diamondbacks");teams.add("Atlanta Braves");
        teams.add("Baltimore Orioles");teams.add("Boston Red Sox");
        teams.add("Chicago Cubs");teams.add("Chicago White Sox");
        teams.add("Cincinnati Reds");teams.add("Cleveland Indians");
        teams.add("Colorado Rockies");teams.add("Detroit Tigers");
        teams.add("Houston Astros");teams.add("Kansas City Royals");
        teams.add("Los Angeles Angels");teams.add("Los Angeles Dodgers");
        teams.add("Miami Marlins");teams.add("Milwaukee Brewers");
        teams.add("Minnesota Twins");teams.add("New York Mets");
        teams.add("New York Yankees");teams.add("Oakland Athletics");
        teams.add("Philadelphia Phillies");teams.add("Pittsburgh Pirates");
        teams.add("San Diego Padres");teams.add("San Francisco Giants");
        teams.add("Seattle Mariners");teams.add("St.Louis Cardinals");
        teams.add("Tampa Bay Rays");teams.add("Texas Rangers");
        teams.add("Toronto Blue Jays");teams.add("Washington Nationals");


        for(String name : teams) {
            List<TeamGame> teamGames = repository.selectByName(name);

            MyFirstWay myFirstWay = MyFirstWay.builder()
                    .teamGames(teamGames)
                    .build();

            for (TeamGame teamGame : calculator.calculate(myFirstWay))
                System.out.println(teamGame);
        }
    }
}