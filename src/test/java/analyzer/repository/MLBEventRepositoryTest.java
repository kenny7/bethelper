package analyzer.repository;

import analyzer.repository.hibernate.MLBEventHibernateRepository;
import analyzer.repository.hibernate.TeamHibernateRepository;
import entity.event.MLBEvent;
import entity.odd.Odd;
import entity.odd.Winner1;
import entity.odd.Winner2;
import entity.score.Run;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

public class MLBEventRepositoryTest {

    MLBEventRepository mlbEventRepository = new MLBEventHibernateRepository();
    TeamRepository teamRepository = new TeamHibernateRepository();

    @Test
    public void get() {
        MLBEvent mlbEvent = mlbEventRepository.get(1l);
        System.out.println(mlbEvent);
    }

    @Test
    public void getAll() {
        System.out.println(mlbEventRepository.getAll());
    }

    @Test
    public void add() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate localDate = LocalDate.parse("05.07.2019", formatter);
        LocalDateTime dateTime = LocalDateTime.of(localDate, LocalTime.of(0, 0));

        List<Run> runs = new LinkedList<>();

        for(int i = 0; i < 9; i++){
            if(i > 5){
                runs.add(Run.builder()
                        .team(teamRepository.get(2l))
                        .build());
            } else {
                runs.add(Run.builder()
                        .team(teamRepository.get(1l))
                        .build());
            }
        }

        List<Odd> odds = new LinkedList<>();
        odds.add(Winner1.winner1Builder()
                .value(1.35)
                .build());
        odds.add(Winner2.winner2Builder()
                .value(2.25)
                .build());

        MLBEvent mlbEvent = MLBEvent.builder()
                .team1(teamRepository.get(1l))
                .team2(teamRepository.get(2l))
                .runs(runs)
                .odds(odds)
                .timestamp(dateTime)
                .build();

        mlbEventRepository.add(mlbEvent);

    }

    @Test
    public void update() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate localDate = LocalDate.parse("06.07.2019", formatter);
        LocalDateTime dateTime = LocalDateTime.of(localDate, LocalTime.of(1, 15));

        MLBEvent mlbEvent = MLBEvent.builder()
                .id(14l)
                .team1(teamRepository.get(1l))
                .team2(teamRepository.get(2l))
                .timestamp(dateTime)
                .build();

        mlbEventRepository.update(mlbEvent);
    }

    @Test
    public void delete() {
        mlbEventRepository.delete(14l);
    }
}