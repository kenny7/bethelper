package repositories;

import analyzer.dao.BaseballGameDAO;
import entity.event.MLBEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import repositories.filter.Filter;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BaseballGameTeamGameRepository{

    private BaseballGameDAO baseballGameDao;
    private List<MLBEvent> MLBEvents;

    public void loadDataToRepository(Filter filter){
        MLBEvents = baseballGameDao.selectByFilter(filter);
    }

    public List<MLBEvent> selectAllBaseballGames(){
        return MLBEvents;
    }
}
