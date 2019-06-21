package analyzer.repository;

import analyzer.repository.hibernate.OddHibernateRepository;
import entity.odd.Odd;
import entity.odd.Winner1;
import entity.odd.Winner2;
import org.junit.Test;

public class OddRepositoryTest {

    OddRepository oddRepository = new OddHibernateRepository();

    @Test
    public void get() {
        Odd odd = oddRepository.get(29l);
        System.out.println(odd);
    }

    @Test
    public void getAll() {
        System.out.println(oddRepository.getAll());
    }

    @Test
    public void add() {
        Odd odd = Winner1.winner1Builder()
                .value(1.55)
                .build();

        oddRepository.add(odd);
    }

    @Test
    public void update() {
        Odd odd = Winner2.winner2Builder()
                .id(1l)
                .value(1.85)
                .build();

        oddRepository.update(odd);
    }

    @Test
    public void delete() {
        oddRepository.delete(1l);
    }
}