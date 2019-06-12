package repositories;

import java.time.LocalDate;

public interface Repository {

    <T> void write(T t);

    <T> T selectById(Long id);

    <T> T selectBeforeDateNotIncluding(LocalDate localDate);

    <T> T selectAllByName(String name);
}
