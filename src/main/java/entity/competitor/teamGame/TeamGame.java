package entity.competitor.teamGame;


import analyzer.repository.hibernate.EvetResultConverter;
import analyzer.repository.hibernate.HomeOrAwayConverter;
import entity.competitor.Team;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Optional;

@Entity(name = "teamGame")
@Table(name = "teamGame")
@NamedQuery(name = "teamGame.getAll", query = "select c from teamGame c")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamGame {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "opponent_id")
    private Team opponent;

    @Column
    private Long eventId;

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

    public Optional<Team> getTeam() {
        return Optional.ofNullable(team);
    }

    public Optional<Team> getOpponent() {
        return Optional.ofNullable(opponent);
    }

    public Optional<Long> getEventId() {
        return Optional.ofNullable(eventId);
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
        return Optional.ofNullable(winCoefficient);
    }

    public Optional<Double> getWinPercent() {
        return Optional.ofNullable(winPercent);
    }

    @Override
    public String toString(){

        StringBuilder result = new StringBuilder(getId().toString()).append("\t");
        result.append(getTeam().get()).append("\t")
                .append(getPlace().get()).append("\t")
                .append(getOpponent().get()).append("\t")
                .append(getRuns().get()).append("\t")
                .append(getMissedRuns().get()).append("\t")
                .append(getResult().get()).append("\t")
                .append(String.format("%.2f", getWinCoefficient().get())).append("\t")
        .append(String.format("%.3f", getWinPercent().get()));

        return result.toString();
    }

}
