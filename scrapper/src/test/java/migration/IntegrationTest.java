package migration;


import org.junit.Test;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.Assert.assertNotNull;


@Testcontainers
public class IntegrationTest extends IntegrationEnvironment {
    @Test
    public void testContainer() {
        assertNotNull(getInstance());
    }

}
