package analyzer.outputData;

import competitor.TeamState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamStateOutput implements OutputData{

    private List<TeamState> teamStates;

    @Override
    public <T> void setOutputData(T t) {

    }

    @Override
    public <T> T getOutputData() {
        return null;
    }

    @Override
    public void submitDataOutput() {

    }
}
