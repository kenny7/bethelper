package analyzer.repository;

import entity.event.MLBEvent;

import java.util.List;

public interface MLBEventRepository extends Repository<MLBEvent>{

    @Override
    default MLBEvent get(Long id) {
        return null;
    }

    @Override
    default List<MLBEvent> getAll() {
        return null;
    }

    @Override
    default MLBEvent add(MLBEvent mlbEvent) {
        return null;
    }

    @Override
    default void delete(Long id) {

    }

    /*MLBEvent selectBaseballGameById(Long id);

    List<MLBEvent> selectAll();

    void writeBaseballGame(MLBEvent MLBEvent);

    void update(MLBEvent MLBEvent);

    void deleteById(Long id);*/

}
