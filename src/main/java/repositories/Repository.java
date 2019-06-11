package repositories;

import java.sql.Timestamp;

public interface Repository {

    <T> void write(T t);

    <T> T selectById(Long id);

    <T> T selectBeforeDateNotIncluding(Timestamp timestamp);
}
