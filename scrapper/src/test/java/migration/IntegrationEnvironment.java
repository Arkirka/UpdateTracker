package migration;

import liquibase.Contexts;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class IntegrationEnvironment {

    private static final String MIGRATION_FILE = "migrations/master.xml";

    private static PostgreSQLContainer<?> container;

    public static synchronized PostgreSQLContainer<?> getInstance() {
        if (container == null) {
            container = new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"));
            container.start();
            runLiquibaseMigration();
        }
        return container;
    }

    private static void runLiquibaseMigration() {
        try {
            Connection connection = getConnection();
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            Liquibase liquibase = new Liquibase(MIGRATION_FILE, new ClassLoaderResourceAccessor(), database);
            liquibase.update(new Contexts());
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to migrate Liquibase", e);
        } catch (Exception e) {
            throw new RuntimeException("Failed to run Liquibase", e);
        }
    }

    private static Connection getConnection() {
        try {
            return DriverManager.getConnection(container.getJdbcUrl(), container.getUsername(), container.getPassword());
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get connection", e);
        }
    }

}
