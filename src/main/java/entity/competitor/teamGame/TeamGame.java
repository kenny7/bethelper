package entity.competitor.teamGame;


import analyzer.parser.MLBStage;
import analyzer.repository.hibernate.EvetResultConverter;
import analyzer.repository.hibernate.HomeOrAwayConverter;
import analyzer.repository.hibernate.MLBStageAttributeConverter;
import entity.competitor.Team;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Entity(name = "teamGame")
@Table(name = "teamGame",
uniqueConstraints = @UniqueConstraint(columnNames = {"date", "team_id", "opponent_id"}))
@NamedQuery(name = "teamGame.getAll", query = "select c from teamGame c")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeamGame {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    private LocalDateTime date;

    @Column
    @Convert(converter = MLBStageAttributeConverter.class)
    private MLBStage stage;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "opponent_id")
    private Team opponent;

    @Column
    @Convert(converter = HomeOrAwayConverter.class)
    private HomeOrAway place;

    @Column
    private Integer runs;

    @Column
    private Integer missedRuns;

    @Column
    @Convert(converter = EvetResultConverter.class)
    private EventResult result;

    @Column
    private Double winCoefficient;

    @Column
    private Double winPercent;

    @Column
    private Double MLBStageWinPercent;

    @Column
    private Double winPercentInLastFiveGames;

    public Optional<Double> getWinPercentInLastFiveGames() {
        return Optional.ofNullable(winPercentInLastFiveGames);
    }

    public Optional<Long> getId(){
        return Optional.ofNullable(id);
    }

    public Optional<Team> getTeam() {
        return Optional.ofNullable(team);
    }

    public Optional<Team> getOpponent() {
        return Optional.ofNullable(opponent);
    }

    public Optional<HomeOrAway> getPlace() {
        return Optional.ofNullable(place);
    }

    public Optional<Integer> getRuns() {
        return Optional.ofNullable(runs);
    }

    public Optional<Integer> getMissedRuns() {
        return Optional.ofNullable(missedRuns);
    }

    public Optional<EventResult> getResult() {
        return Optional.ofNullable(result);
    }

    public Optional<Double> getWinCoefficient() {

        if(winCoefficient != null)
            return Optional.ofNullable(winCoefficient);
        else
            return Optional.ofNullable(0.0);
    }

    public Optional<Double> getWinPercent() {
        if(winPercent != null)
            return Optional.ofNullable(winPercent);
        else
            return Optional.ofNullable(0.0);
    }

    @Override
    public String toString(){

        StringBuilder result = new StringBuilder();

        String s = getId().isPresent() ? getId().get().toString() : "no id";
        result.append(s).append("\t");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH.mm");
        s = getDate().format(formatter);
        result.append(s).append("\t");

        s = getTeam().get().getName().get();
        result.append(s).append("\t");

        s = getPlace().get().name();
        result.append(s).append("\t");

        s = getOpponent().get().getName().get();
        result.append(s).append("\t");

        s = getRuns().get().toString();
        result.append(s).append("\t");

        s = getMissedRuns().get().toString();
        result.append(s).append("\t");

        s = getResult().get().name();
        result.append(s).append("\t");

        s = String.format("%.2f", getWinCoefficient().get());
        result.append(s).append("\t");

        s = String.format("%.3f",
                getWinPercent().isPresent() ? getWinPercent().get() : 0.0);
        result.append(s).append("\t");

        s = String.format("%.3f",
                getWinPercentInLastFiveGames().isPresent() ? getWinPercentInLastFiveGames().get() : 0.0);
        result.append(s).append("\t");

        return result.toString();
    }

}
