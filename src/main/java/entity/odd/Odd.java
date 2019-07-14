package entity.odd;

import lombok.*;

import javax.persistence.*;
import javax.swing.text.html.Option;
import java.util.Optional;

@Entity(name = "odd")
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "odd")
@NamedQuery(name = "odd.getAll", query = "select c from odd c")
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Odd {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "odd_sequence")
    protected Long id;

    @Column(name = "value")
    protected Double value;

    public Optional<Double> getValue() {
        return Optional.ofNullable(value);
    }

    @Override
    public String toString(){
        StringBuilder result = new StringBuilder();
        result.append(this.getClass().getSimpleName()).append("\t");

        String s = String.format("%.3f", getValue().get());
        result.append(s);

        return result.toString();
    }
}
