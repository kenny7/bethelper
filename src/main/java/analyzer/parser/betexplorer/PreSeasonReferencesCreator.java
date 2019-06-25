package analyzer.parser.betexplorer;

import analyzer.parser.MLBStage;

import java.util.List;

public class PreSeasonReferencesCreator extends StageGameReferencesCreator{

    public PreSeasonReferencesCreator(String reference, MLBStage stage) {
        super(reference, stage);
    }

    public PreSeasonReferencesCreator() {
    }

    @Override
    public List<String> getReferences() {
        return null;
    }
}
