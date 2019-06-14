package analyzer.dao;

import repositories.filter.Filter;

import java.util.List;

public interface BaseballGameDAO<BaseballGame> {

    BaseballGame selectById(Long id);

    List<BaseballGame> selectByFilter(Filter filter);

    void write(BaseballGame baseballGame);

    void delete(BaseballGame baseballGame);

}
