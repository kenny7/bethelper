package analyzer.dao;

import repositories.filter.Filter;

import java.util.List;

public interface TeamGameDAO<TeamGame> {

    TeamGame selectById(Long id);

    List<TeamGame> selectByFilter(Filter filter);

    void write(TeamGame baseballGame);

    void delete(TeamGame baseballGame);

}
