package dev.boooiil.historia.core.database.sql;

import java.sql.ResultSet;

public interface IResultProcessor<T> {
    T process(ResultSet resultSet);
}
