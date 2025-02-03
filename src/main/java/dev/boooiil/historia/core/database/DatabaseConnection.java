package dev.boooiil.historia.core.database;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariDataSource;

import dev.boooiil.historia.core.util.Logging;

public abstract class DatabaseConnection implements IDatabaseConnection {

    protected HikariDataSource dataSource;
    protected Connection connection;
    protected boolean errored;

    public enum DatabaseType {
        MYSQL("mysql"),
        SQLITE("sqlite"),
        UNKNOWN("unknown");

        private final String key;

        DatabaseType(String key) {

            this.key = key;

        }

        public String getKey() {

            return this.key;

        }

        public static DatabaseType fromString(String key) {

            for (DatabaseType type : DatabaseType.values()) {

                if (type.getKey().equalsIgnoreCase(key)) {

                    return type;

                }

            }

            return UNKNOWN;

        }

    }

    public abstract boolean initDataSource();

    public DatabaseConnection() {
    };

    public abstract DatabaseType getDatabaseType();

    public boolean isErrored() {
        return errored;
    };

    public boolean closeConnection() {

        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                Logging.debugToConsole("Closed", getDatabaseType().toString(), "connection.");
            }
            return true;
        } catch (SQLException sqlException) {

            exceptionLogger(sqlException, "Failed to close connection.");
            return false;
        }

    }

    public boolean connect() {

        Logging.debugToConsole("Connecting to database...");

        try {
            connection = dataSource.getConnection();

            if (connection != null) {
                Logging.debugToConsole("Connected to database.");
                return true;
            } else {
                Logging.errorToConsole("Failed to connect to database.");
            }
        }

        catch (SQLException sqlException) {
            exceptionLogger(sqlException, "Failed to connect to database.");

        }

        return false;

    }

    public boolean closeDataSource() {

        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
            Logging.debugToConsole("Closed", getDatabaseType().toString(), "data source.");
        }

        return true;

    }

    public Connection getConnection() {

        try {
            if (dataSource != null && !dataSource.isClosed()) {
                connection = dataSource.getConnection();
                return connection;
            }
            return null;
        } catch (SQLException sqlException) {
            exceptionLogger(sqlException, "Failed to get connection.");
            return null;
        }

    }

    public boolean reconnect() {

        try {

            Logging.warnToConsole("Attempting to close the connection...");
            connection.close();
            Logging.warnToConsole("Connection closed.");

            Logging.warnToConsole("Attempting to reconnect...");
            connection = dataSource.getConnection();
            Logging.warnToConsole("Reconnected to SQL Server.");
            return true;
        } catch (SQLException sqlException) {

            exceptionLogger(sqlException, "Failed to reconnect.");
            return false;
        }
    }

    public void executor(String statement) {

        Logging.debugToConsole("Executing:", statement);

        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(statement);

            preparedStatement.execute();
        } catch (SQLException sqlException) {

            exceptionLogger(sqlException, "Failed to execute:" + statement);

        }

        // TODO: create a cache or something to handle stale connections while the
        // server is currently running
    }

    public <T> T queryExecutor(String statement, IResultProcessor<T> resultProcessor) {

        try (Connection connection = getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement(statement);
            ResultSet resultSet = preparedStatement.executeQuery();

            return resultProcessor.process(resultSet);

        } catch (SQLException sqlException) {
            exceptionLogger(sqlException, "Failed to execute query:" + statement);

            return null;
        }

    }

    public <T> T queryExecutor(String statement, IResultProcessor<T> resultProcessor, int maxRetry) {
        return queryExecutor(statement, resultProcessor, maxRetry, 0);
    }

    public <T> T queryExecutor(String statement, IResultProcessor<T> resultProcessor, int maxRetry, int curr) {

        try (Connection connection = getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement(statement);
            ResultSet resultSet = preparedStatement.executeQuery();

            return resultProcessor.process(resultSet);

        } catch (SQLException sqlException) {
            exceptionLogger(sqlException, "Failed to execute query:" + statement);

            return curr > maxRetry ? queryExecutor(statement, resultProcessor, maxRetry, curr) : null;
        }

    }

    public void updateExecutor(String statement) {

        Logging.debugToConsole("Executing update query:", statement);

        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(statement);

            preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {

            exceptionLogger(sqlException, "Failed to execute update:" + statement);

        }

        // TODO: create a cache or something to handle stale connections while the
        // server is currently running
    }

    public void updateExecutor(String statement, int maxRetry) {
        updateExecutor(statement, maxRetry, 0);
    }

    public void updateExecutor(String statement, int maxRetry, int curr) {

        Logging.debugToConsole("Executing update query:", statement, "with max retries: " + maxRetry,
                "and current retries: " + curr);

        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(statement);

            preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {

            exceptionLogger(sqlException, "Failed to execute update: " + statement + "Retry " + ++curr + "/"
                    + maxRetry);

            updateExecutor(statement, maxRetry, curr);

        }

        // TODO: create a cache or something to handle stale connections while the
        // server is currently running
    }

    public boolean nextResult(ResultSet result) {

        Logging.debugToConsole("Trying next result...");

        try {
            return result.next();
        } catch (SQLException sqlException) {
            exceptionLogger(sqlException, "Failed to process next result");
            return false;
        }

    }

    public <T> T getResult(ResultSet result, int column, Class<T> clazz) {
        try {

            ResultSetMetaData resultSetMetaData = result.getMetaData();

            return getResult(result, resultSetMetaData.getColumnName(column), clazz);

        } catch (SQLException sqlException) {
            exceptionLogger(sqlException, "Failed to get result: " + column);
            return null;
        }
    };

    public <T> T getResult(ResultSet result, String columnName, Class<T> clazz) {

        try {
            if (clazz == String.class) {
                return clazz.cast(result.getString(columnName));
            } else if (clazz == Integer.class) {
                return clazz.cast(result.getInt(columnName));
            } else if (clazz == Double.class) {
                return clazz.cast(result.getDouble(columnName));
            } else if (clazz == Float.class) {
                return clazz.cast(result.getFloat(columnName));
            } else if (clazz == Boolean.class) {
                return clazz.cast(result.getBoolean(columnName));
            } else if (clazz == Long.class) {
                return clazz.cast(result.getLong(columnName));
            } else if (clazz == Array.class) {
                return clazz.cast(result.getArray(columnName));
            } else {
                Logging.errorToConsole("Unimplemented result type:", clazz.getName());
                return null;
            }
        } catch (SQLException sqlException) {
            exceptionLogger(sqlException, "Failed to get result: " + columnName);
            return null;
        }
    };

    protected void exceptionLogger(SQLException sqlE, String leadingMessage) {

        Logging.errorToConsole("[", getDatabaseType().toString(), "]", leadingMessage);
        Logging.errorToConsole("[", getDatabaseType().toString(), "]", "Cause: " + sqlE.getCause());
        Logging.errorToConsole("[", getDatabaseType().toString(), "]", "Error Code: " + sqlE.getErrorCode());
        Logging.errorToConsole("[", getDatabaseType().toString(), "]", "Error Message: " + sqlE.getMessage());

    }

}
