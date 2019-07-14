package analyzer.repository;

import entity.event.MLBEvent;

import java.time.LocalDateTime;
import java.util.List;

public interface MLBEventRepository extends Repository<MLBEvent>{

    @Override
    MLBEvent get(Long id);

    @Override
    List<MLBEvent> getAll();

    @Override
    MLBEvent add(MLBEvent mlbEvent);

    @Override
    void delete(Long id);

    List<MLBEvent> selectMLBEventBetweenDate(
            LocalDateTime startDate, LocalDateTime finishDate);
}
