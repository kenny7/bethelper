package entity.odd;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Odd {

    private Long id;
    private Double value;

    @Override
    public String toString() {
        if(value != null)
            return value.toString();
        else
            return "none";
    }
}
