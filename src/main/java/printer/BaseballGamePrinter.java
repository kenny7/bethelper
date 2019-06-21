package printer;

import entity.event.MLBEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BaseballGamePrinter implements Printer {

    private List<MLBEvent> MLBEvents;

    @Override
    public void printData() {
        for(MLBEvent MLBEvent : MLBEvents)
            System.out.println(MLBEvent);
    }

}
