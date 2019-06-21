package entity.odd;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@Data
@NoArgsConstructor
public class Winner2 extends Odd{

    @Builder(builderMethodName = "winner2Builder")
    public Winner2(Long id, Double value) {
        super(id, value);
    }

    @Override
    public String toString(){
        return super.toString();
    }
}
