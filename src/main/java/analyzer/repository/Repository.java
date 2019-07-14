package analyzer.repository;

import org.hibernate.Hibernate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public interface Repository<T> {

    EntityManager em = Persistence.createEntityManagerFactory("baseball").createEntityManager();

    T get(Long id);

    List<T> getAll();

    T add(T t);

    void update(T t);

    void delete(Long id);

}
