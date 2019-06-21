package analyzer.repository.hibernate;

import entity.competitor.teamGame.EventResult;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class EvetResultConverter implements AttributeConverter<EventResult, String> {

    @Override
    public String convertToDatabaseColumn(EventResult eventResult) {
        return eventResult == null ? null : eventResult.toString();
    }

    @Override
    public EventResult convertToEntityAttribute(String s) {
        EventResult result = EventResult.WIN;

        switch (s){
            case "win" : result = EventResult.WIN;
            case "lose" : result= EventResult.LOSE;
        }

        return result;
    }
}
