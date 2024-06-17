package br.com.erudio.integrationtests.testcontainers;

import java.util.Map;
import java.util.stream.Stream;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.lifecycle.Startables;

//@ContextConfiguration(initializers = AbstractIntegrationTest.Initializer.class)
public class AbstractIntegrationTest {

/*	static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
		
		static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0.32")
                .withDatabaseName("integration-tests-db")
                .withUsername("sa")
                .withPassword("sa");
		
		private static void startContainers() {
			Startables.deepStart(Stream.of(mysql)).join();
		}
		
		private static Map<String, String> createConnectionConfiguration() {
			return Map.of(
					"spring.datasource.url", mysql.getJdbcUrl(),
					"spring.datasource.url.username", mysql.getUsername(),
					"spring.datasource.url.password", mysql.getPassword()
					);
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		public void initialize(ConfigurableApplicationContext applicationContext) {
			startContainers();
			ConfigurableEnvironment environment = applicationContext.getEnvironment();
			MapPropertySource testcontainers = new MapPropertySource("testcontainers", (Map) createConnectionConfiguration());
			environment.getPropertySources().addFirst(testcontainers);
		}

	}
*/
}
