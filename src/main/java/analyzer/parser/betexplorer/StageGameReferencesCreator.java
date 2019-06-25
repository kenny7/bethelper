package analyzer.parser.betexplorer;

import analyzer.parser.MLBStage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class StageGameReferencesCreator {

    private String reference;
    private MLBStage stage;

    public abstract List<String> getReferences();

}
