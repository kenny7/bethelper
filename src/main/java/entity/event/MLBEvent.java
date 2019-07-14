package entity.event;

import analyzer.parser.MLBStage;
import analyzer.repository.hibernate.MLBStageAttributeConverter;
import entity.competitor.Team;
import lombok.*;
import entity.odd.Odd;
import entity.score.Run;

import javax.persistence.*;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "mlbEvent")
@Table(name = "MLBEvent",
uniqueConstraints = {@UniqueConstraint(columnNames = {"team1_id", "team2_id", "date"})})
@NamedQuery(name = "mlbevent.getAll", query = "select c from mlbEvent c")
public class MLBEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mlbevent_sequence")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "team1_id", referencedColumnName = "team_id")
    private Team team1;

    @ManyToOne
    @JoinColumn(name = "team2_id", referencedColumnName = "team_id")
    private Team team2;

    /*@OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "mlbevent_team",
    joinColumns = @JoinColumn(name = "mlbevent_id"),
    inverseJoinColumns = @JoinColumn(name = "team_id"))
    private Set<Team> teams;*/

    @Column(name = "date")
    private LocalDateTime timestamp;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinTable(
            name = "mlbevent_run",
            joinColumns = @JoinColumn(name = "mlbevent_id"),
            inverseJoinColumns = @JoinColumn(name = "run_id")
    )
    private List<Run> runs;

    @OneToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "mlbevent_odd",
            joinColumns = @JoinColumn(name = "mlbevent_id"),
            inverseJoinColumns = @JoinColumn(name = "odd_id")
    )
    private List<Odd> odds;

    @Column
    @Convert(converter = MLBStageAttributeConverter.class)
    private MLBStage MLBStage;

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

    private Integer countRuns(Team team){
        int countRuns = 0;

        for (Run run : getRuns().get()){
            if(team.equals(run.getTeam().get()))
                countRuns++;
        }

        return countRuns;
    }

    @Override
    public String toString() {

        StringBuilder result = new StringBuilder();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

        result.append(getId()).append("\t")
                .append(getTeam1().get().toString()).append(" - ")
                .append(getTeam2().get().toString()).append("\t")
                .append(getTimestamp().get().format(formatter)).append("\t");

        result.append(countRuns(getTeam1().get()))
                .append(" : ")
                .append(countRuns(getTeam2().get()))
                .append("\t");

        String odd = getOdds().isPresent() ? getOdds().toString() : "no odds";

        result.append(odd);


        return result.toString();
    }
}
