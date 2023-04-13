package migration;


import org.junit.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.Assert.assertNotNull;


@Testcontainers
public class IntegrationTest {

    @Container
    public PostgreSQLContainer<?> postgresqlContainer = IntegrationEnvironment.getInstance();

    @Test
    public void testContainer() {
        assertNotNull(postgresqlContainer);
    }

}
