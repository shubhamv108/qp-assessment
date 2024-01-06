package code.shubham.commons;

import code.shubham.TemplateServiceJavaSpringBootApplication;
import code.shubham.commons.contexts.RoleContextHolder;
import code.shubham.commons.contexts.UserIDContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

@SpringBootTest(classes = TemplateServiceJavaSpringBootApplication.class)
public abstract class AbstractTest {

	@Autowired
	protected TestKafkaConsumer kafkaConsumer;

	@Autowired
	private EntityManagerRepository entityManagerRepository;

	protected void setUp() {
		RoleContextHolder.set(Set.of());
		UserIDContextHolder.set(null);
	}

	protected void truncate(final String table) {
		this.entityManagerRepository.truncateTable(table);
	}

}
