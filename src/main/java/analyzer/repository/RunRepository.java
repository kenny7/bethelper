package analyzer.repository;

import entity.score.Run;

import java.util.List;

public interface RunRepository extends Repository<Run>{

    Run get(Long id);

    List<Run> getAll();

    Run add(Run run);

    void update(Run run);

    void delete(Long id);

}
