package entity.competitor;

import lombok.*;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.SelectBeforeUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "team")
@Table(name = "team",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
@NamedQuery(name = "team.getAll", query = "select c from team c")
public class Team implements Serializable {

    @Id
    @Column(name = "team_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "team_sequence")
    @EqualsAndHashCode.Exclude
    private Long id;

    @Column(name = "name", length = 50, unique = true)
    @EqualsAndHashCode.Include
    private String name;

    public Team(String name) {
        this.name = name;
    }

    public Optional<Long> getId() {
        return Optional.ofNullable(id);
    }

    public Optional<String> getName() {
        return Optional.ofNullable(name);
    }

    @Override
    public String toString() {
        return name;
    }
}
