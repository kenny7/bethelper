package analyzer.repository.hibernate;

import entity.competitor.teamGame.HomeOrAway;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class HomeOrAwayConverter implements AttributeConverter<HomeOrAway, String> {

    @Override
    public String convertToDatabaseColumn(HomeOrAway homeOrAway) {
        return homeOrAway == null ? null : homeOrAway.toString();
    }

    @Override
    public HomeOrAway convertToEntityAttribute(String s) {
        HomeOrAway place = HomeOrAway.HOME;

        if(s.equals("away"))
            place = HomeOrAway.AWAY;

        return place;
    }
}
