package repositories;

import analyzer.dao.BaseballGameDAO;
import entity.event.BaseballGame;
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
    private List<BaseballGame> baseballGames;

    public void loadDataToRepository(Filter filter){
        baseballGames = baseballGameDao.selectByFilter(filter);
    }

    public List<BaseballGame> selectAllBaseballGames(){
        return baseballGames;
    }
}
