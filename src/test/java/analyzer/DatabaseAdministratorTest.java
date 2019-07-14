package analyzer;

import analyzer.parser.betexplorer.MLBEventParser;
import analyzer.parser.betexplorer.Parser;
import analyzer.parser.betexplorer.ParserMLBEventFromBetExplorer;
import analyzer.repository.hibernate.MLBEventHibernateRepository;
import analyzer.repository.hibernate.RunHibernateRepository;
import analyzer.repository.hibernate.TeamGameHibernateRepository;
import analyzer.repository.hibernate.TeamHibernateRepository;
import analyzer.teamGameCreator.TeamGameCreator;
import entity.competitor.Team;
import entity.competitor.teamGame.EventResult;
import entity.competitor.teamGame.TeamGame;
import entity.event.MLBEvent;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;

public class DatabaseAdministratorTest {

    DatabaseAdministrator databaseAdministrator = DatabaseAdministrator.builder()
            .mlbEventRepository(new MLBEventHibernateRepository())
            .teamRepository(new TeamHibernateRepository())
            .teamGameRepository(new TeamGameHibernateRepository())
            .runRepository(new RunHibernateRepository())
            .build();

    ParserMLBEventFromBetExplorer mlbParser
            = ParserMLBEventFromBetExplorer.builder()
            .build();

    Parser parser = new Parser(5);
    AnalyzerAdministrator analyzerAdministrator = AnalyzerAdministrator.builder()
            .teamGameCreator(new TeamGameCreator())
            .databaseAdministrator(databaseAdministrator)
            .build();

    @Test
    public void addMLBEvents() {

        List<MLBEvent> events = parser.parseMLB("2018");

        /*events = parser.parseMLBEventStage(StageGameReferencesCreator.creatorFactory(
                MLBStage.MAIN, "https://www.betexplorer.com/baseball/usa/mlb-2018/results/?stage=KvfZSOKj"
        ));*/

        System.out.println(events.size());

        databaseAdministrator.addMLBEvents(events);
    }

    @Test
    public void addMLBEvent(){

        MLBEventParser mlbEventParser = new MLBEventParser();

        MLBEvent event
                = mlbEventParser
                .parse("https://www.betexplorer.com/baseball/usa/mlb-2018/san-diego-padres-colorado-rockies/UNOkLOf8/");
        System.out.println(event);

        MLBEvent event2 = mlbEventParser
                .parse("https://www.betexplorer.com/baseball/usa/mlb/los-angeles-dodgers-san-diego-padres/Kt3PvnkU/");
        System.out.println(event2);

        databaseAdministrator.addMLBEvent(event);
        databaseAdministrator.addMLBEvent(event2);

        System.out.println(databaseAdministrator.selectAllMLBEvents());

    }

    @Test
    public void addTeamGames(){

        LocalDateTime startDate = LocalDateTime.of(2018, 01, 01, 00, 00);
        LocalDateTime finishDate = LocalDateTime.of(2018, 12, 31, 23, 59);

        List<MLBEvent> events = databaseAdministrator.selectMLBEventsBetweenDate(startDate, finishDate);
        List<TeamGame> teamGames = analyzerAdministrator.createTeamGames(events);

        databaseAdministrator.addTeamGames(teamGames);

    }

    @Test
    public void selectTeamGamesBetweenDatesByTeamId(){

        LocalDateTime startDate = LocalDateTime.of(2018, 01, 01, 00, 00);
        LocalDateTime finishDate = LocalDateTime.of(2018, 03, 29, 18, 41);
        List<TeamGame> teamGames = databaseAdministrator.selectTeamGamesBetweenDatesByTeamId(20l, startDate, finishDate);

        for(TeamGame teamGame : teamGames)
            System.out.println(teamGame);
    }

    @Test
    public void selectTeamGamesBetweenDatesByTeam(){
        LocalDateTime startDate = LocalDateTime.of(2018, 01, 01, 00, 00);
        LocalDateTime finishDate = LocalDateTime.of(2018, 12, 31, 23, 59);
        Team team = databaseAdministrator.selectTeamByName("Colorado Rockies");


        List<TeamGame> teamGames = databaseAdministrator
                .selectTeamGamesBetweenDatesByTeam(team, startDate, finishDate);

        for(TeamGame teamGame : teamGames)
            System.out.println(teamGame);
    }

    @Test
    public void selectTeamGamesBetweenDatesByTeamAndResult(){
        LocalDateTime startDate = LocalDateTime.of(2018, 01, 01, 00, 00);
        LocalDateTime finishDate = LocalDateTime.of(2018, 12, 31, 23, 59);
        EventResult result = EventResult.WIN;
        Team team = databaseAdministrator.selectTeamByName("Colorado Rockies");

        List<TeamGame> teamGames = databaseAdministrator
                .selectTeamGameBetweenDatesByTeamAndResult(team, result, startDate, finishDate);

        for(TeamGame teamGame : teamGames)
            System.out.println(teamGame);
    }
}