package dev.boooiil.historia.core.database;

import java.sql.Connection;

public interface DatabaseConnection<T> {

    public Connection getConnection();

    public boolean connect();

    public void closeConnection();

    public void closeDataSource();

    public void reconnect();

    public void initDataSource();

}
