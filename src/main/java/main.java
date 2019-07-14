import analyzer.DatabaseAdministrator;
import analyzer.repository.hibernate.MLBEventHibernateRepository;
import analyzer.repository.hibernate.TeamGameHibernateRepository;
import analyzer.repository.hibernate.TeamHibernateRepository;
import entity.event.MLBEvent;

import java.util.List;

public class main {

    DatabaseAdministrator databaseAdministrator =
            DatabaseAdministrator.builder()
                    .teamGameRepository(new TeamGameHibernateRepository())
                    .mlbEventRepository(new MLBEventHibernateRepository())
                    .teamRepository(new TeamHibernateRepository())
                    .build();

    public static void main(String[] args) {

        List<MLBEvent> events;

    }
}
