package analyzer.repository;

import entity.odd.Odd;

import java.util.List;

public interface OddRepository extends Repository<Odd>{

    Odd get(Long id);

    List<Odd> getAll();

    Odd add(Odd odd);

    void update(Odd odd);

    void delete(Long id);
}
