package entity.score;

import com.sun.istack.internal.NotNull;
import entity.competitor.Team;
import lombok.*;

import javax.persistence.*;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "run")
@Table(name = "run")
@org.hibernate.annotations.NamedQuery(name = "run.getAll", query = "select c from run c")
public class Run {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "run_sequence")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "team_id", referencedColumnName = "team_id")
    private Team team;

    public Run(Team team) {
        this.team = team;
    }

    public Optional<Team> getTeam() {
        return Optional.ofNullable(team);
    }

    @Override
    public String toString() {
        return "run " + id + " : " + team;
    }
}
