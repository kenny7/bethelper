package analyzer;

import analyzer.parser.MLBStage;
import analyzer.parser.betexplorer.Parser;
import analyzer.parser.betexplorer.ParserMLBEventFromBetExplorer;
import analyzer.parser.betexplorer.StageGameReferencesCreator;
import analyzer.repository.hibernate.MLBEventHibernateRepository;
import analyzer.repository.hibernate.TeamHibernateRepository;
import entity.event.MLBEvent;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class DatabaseAdministratorTest {

    DatabaseAdministrator databaseAdministrator = DatabaseAdministrator.builder()
            .mlbEventRepository(new MLBEventHibernateRepository())
            .teamRepository(new TeamHibernateRepository())
            .build();

    ParserMLBEventFromBetExplorer mlbParser
            = ParserMLBEventFromBetExplorer.builder()
            .build();

    Parser parser = new Parser(5);

    @Test
    public void addMLBEvents() {

        List<MLBEvent> events = parser.parseMLB("2018"
        );

        System.out.println(events.size());

        databaseAdministrator.addMLBEvents(events);

    }
}