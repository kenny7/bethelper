package entity.score;

import entity.competitor.Team;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "run")
@Table(name = "run")
@org.hibernate.annotations.NamedQuery(name = "run.getAll", query = "select c from run c")
public class Run {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "team_id")
    private Team team;

    public Run(Team team) {
        this.team = team;
    }
}
