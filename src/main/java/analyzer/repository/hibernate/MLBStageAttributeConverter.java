package analyzer.repository.hibernate;

import analyzer.parser.MLBStage;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class MLBStageAttributeConverter implements AttributeConverter<MLBStage, String> {

    @Override
    public String convertToDatabaseColumn(MLBStage mlbStage) {
        return mlbStage == null ? null : mlbStage.toString();
    }

    @Override
    public MLBStage convertToEntityAttribute(String s) {
        MLBStage stage = null;

        switch (s){

            case "PRE_SEASON" : stage = MLBStage.PRE_SEASON;
            break;
            case "MAIN" : stage = MLBStage.MAIN;
            break;
            case "WILD_CARD" : stage = MLBStage.WILD_CARD;
            break;
            case "PLAY_OFF" : stage = MLBStage.PLAY_OFF;
            break;
            case "ALL_STARS" : stage = MLBStage.ALL_STARS;

        }
        return stage;
    }
}
