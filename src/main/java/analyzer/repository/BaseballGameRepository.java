package analyzer.repository;

import entity.event.BaseballGame;

import java.util.List;

public interface BaseballGameRepository {

    BaseballGame selectBaseballGameById(Long id);

    List<BaseballGame> selectAll();

    void writeBaseballGame(BaseballGame baseballGame);

    void update(BaseballGame baseballGame);

    void deleteById(Long id);

}
