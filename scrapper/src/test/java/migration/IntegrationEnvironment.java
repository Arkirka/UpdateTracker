package migration;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.core.PostgresDatabase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.DirectoryResourceAccessor;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.lifecycle.Startables;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class IntegrationEnvironment {

    private static final String MIGRATION_FILE = "scrapper/src/main/resources/db/changelog/migrations";

    private static final PostgreSQLContainer<?> container;

    static {
        container = new PostgreSQLContainer<>("postgres:15-alpine")
                .withDatabaseName("scrapper")
                .withUsername("postgres")
                .withPassword("postgres");
        container.start();
        Startables.deepStart(container)
                .thenAccept(unused -> runLiquibaseMigration());
    }

    public PostgreSQLContainer<?> getInstance() {
        return container;
    }
    private static void runLiquibaseMigration() {
        Path changelogPath = new File(".").toPath().toAbsolutePath().
                getParent().getParent().resolve(MIGRATION_FILE);

        try{
            var connection = getConnection();

            var database = new PostgresDatabase();
            database.setConnection(new JdbcConnection(connection));

            var liquibase = new Liquibase("master.xml",
                    new DirectoryResourceAccessor(changelogPath), database);

            liquibase.update(new Contexts(), new LabelExpression());

        } catch (SQLException | FileNotFoundException | LiquibaseException e) {
            throw new RuntimeException(e);
        }
    }

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                container.getJdbcUrl(),
                container.getUsername(),
                container.getPassword()
        );
    }

    @DynamicPropertySource
    static void jdbcProperties(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.username", container::getUsername);
        registry.add("spring.datasource.password", container::getPassword);
    }

}
