package analyzer;

import analyzer.repository.hibernate.MLBEventHibernateRepository;
import analyzer.repository.hibernate.RunHibernateRepository;
import analyzer.repository.hibernate.TeamGameHibernateRepository;
import analyzer.repository.hibernate.TeamHibernateRepository;
import analyzer.teamGameCreator.TeamGameCreator;
import entity.competitor.Team;
import entity.competitor.teamGame.TeamGame;
import entity.event.MLBEvent;
import org.junit.Test;
import java.time.LocalDateTime;
import java.util.List;

public class AnalyzerAdministratorTest {

    DatabaseAdministrator databaseAdministrator =
            DatabaseAdministrator.builder()
                    .teamRepository(new TeamHibernateRepository())
                    .mlbEventRepository(new MLBEventHibernateRepository())
                    .teamGameRepository(new TeamGameHibernateRepository())
                    .runRepository(new RunHibernateRepository())
                    .build();

    AnalyzerAdministrator analyzerAdministrator = AnalyzerAdministrator.builder()
            .databaseAdministrator(databaseAdministrator)
            .teamGameCreator(new TeamGameCreator())
            .build();

    @Test
    public void createTwoTeamGamesFromMLBEvent() {

        MLBEvent event = databaseAdministrator.selectMLBEventById(129999l);

        System.out.println(event);

        List<TeamGame> teamGames =
                analyzerAdministrator.createTwoTeamGamesFromMLBEvent(event);

        for(TeamGame teamGame : teamGames)
            System.out.println(teamGame);
    }

    @Test
    public void createTeamGames(){
        LocalDateTime startDate = LocalDateTime.of(2018, 01, 01, 00, 00);
        LocalDateTime finishDate = LocalDateTime.of(2018, 12, 31, 23, 59);


        List<MLBEvent> events = databaseAdministrator.selectMLBEventsBetweenDate(startDate, finishDate);

        List<TeamGame> teamGames = analyzerAdministrator.createTeamGames(events);

        databaseAdministrator.addTeamGames(teamGames);

    }

    @Test
    public void calculateWinPercent(){

        LocalDateTime startDate = LocalDateTime.of(2018, 01, 01, 00, 00);
        LocalDateTime finishDate = LocalDateTime.of(2018, 12, 31, 23, 59);
        Team team = databaseAdministrator.selectTeamByName("Colorado Rockies");

        Double winPercent = analyzerAdministrator.calculateWinPercent(team, startDate, finishDate);

        System.out.println(winPercent);

    }

    @Test
    public void calculateAndSetWinPercent(){

        TeamGame teamGame = databaseAdministrator.selectTeamGameById(5232l);
        System.out.println(teamGame);

        LocalDateTime startDate = LocalDateTime.of(2018, 5, 01, 00, 00);

        teamGame = analyzerAdministrator.calculateAndSetWinPercent(teamGame, startDate);

        System.out.println(teamGame);

    }

    @Test
    public void calculateAndSetWinPercentForTeamGameList(){

        LocalDateTime startDate = LocalDateTime.of(2018, 01, 01, 00, 00);
        LocalDateTime finishDate = LocalDateTime.of(2018, 12, 31, 23, 59);
        Team team = databaseAdministrator.selectTeamByName("Colorado Rockies");

        List<TeamGame> teamGames = databaseAdministrator
                .selectTeamGamesBetweenDatesByTeam(team, startDate, finishDate);

        List<TeamGame> result = analyzerAdministrator.calculateAndSetWinPercentForTeamGameList(teamGames, startDate);

        for (TeamGame teamGame : result)
            System.out.println(teamGame);
    }

    @Test
    public void getLastNTeamGames(){

        LocalDateTime startDate = LocalDateTime.of(2018, 01, 01, 00, 00);
        LocalDateTime finishDate = LocalDateTime.of(2018, 12, 31, 23, 59);
        Team team = databaseAdministrator.selectTeamByName("Colorado Rockies");

        List<TeamGame> teamGames = databaseAdministrator
                .selectTeamGamesBetweenDatesByTeam(team, startDate, finishDate);

        System.out.println(teamGames.size());

        List<TeamGame> result = analyzerAdministrator.getLastNTeamGames(teamGames, 5);

        for (TeamGame teamGame : result)
            System.out.println(teamGame);

    }

    @Test
    public void calculateWinPercentForLastNGames(){

        LocalDateTime startDate = LocalDateTime.of(2018, 01, 01, 00, 00);
        LocalDateTime finishDate = LocalDateTime.of(2018, 12, 31, 23, 59);
        Team team = databaseAdministrator.selectTeamByName("Colorado Rockies");

        Double d = analyzerAdministrator
                .calculateWinPercentForLastNGames(team, 5, startDate, finishDate);

        System.out.println(d);

    }
}