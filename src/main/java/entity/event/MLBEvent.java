package entity.event;

import entity.competitor.Team;
import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import entity.odd.Odd;
import entity.score.Run;
import org.hibernate.annotations.Columns;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "mlbEvent")
@Table(name = "MLBEvent")
@NamedQuery(name = "mlbevent.getAll", query = "select c from mlbEvent c")
public class MLBEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "team1_id")
    private Team team1;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "team2_id")
    private Team team2;

    @Column(name = "date")
    private LocalDateTime timestamp;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "mlbevent_run",
            joinColumns = @JoinColumn(name = "mlbevent_id"),
            inverseJoinColumns = @JoinColumn(name = "run_id")
    )
    private List<Run> runs;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "mlbevent_odd",
            joinColumns = @JoinColumn(name = "mlbevent_id"),
            inverseJoinColumns = @JoinColumn(name = "odd_id")
    )
    private List<Odd> odds;

    @Transient
    private LocalDate localDate;

    @Transient
    private List<Run> secondTeamRun;

    @Transient
    private Odd coefficientOfWin1;

    @Transient
    private Odd coefficientOfWin2;

    public Optional<Team> getTeam1() {
        return Optional.ofNullable(team1);
    }

    public Optional<Team> getTeam2() {
        return Optional.ofNullable(team2);
    }

    public Optional<LocalDateTime> getTimestamp() {
        return Optional.ofNullable(timestamp);
    }

    public Optional<List<Run>> getRuns(){
        return Optional.ofNullable(runs);
    }

    public Optional<List<Odd>> getOdds(){
        return Optional.ofNullable(odds);
    }

    @Override
    public String toString() {

        StringBuilder result = new StringBuilder();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        result.append(getId()).append("\t")
                .append(getTeam1().get().toString()).append(" - ").append(getTeam2().get().toString()).append("\t")
                .append(getTimestamp().get().format(formatter));

        return result.toString();
    }
}
