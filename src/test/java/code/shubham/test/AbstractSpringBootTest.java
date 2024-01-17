package code.shubham.test;

import code.shubham.GroceryBookingApplication;
import code.shubham.commons.contexts.RoleContextHolder;
import code.shubham.commons.contexts.UserIDContextHolder;
import code.shubham.commons.kafka.KafkaPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

@SpringBootTest(classes = GroceryBookingApplication.class)
public abstract class AbstractSpringBootTest extends AbstractTest {

	protected TestKafkaConsumer kafkaConsumer;

	@Autowired
	protected KafkaPublisher kafkaPublisher;

	@Autowired
	private EntityManagerRepository entityManagerRepository;

	protected void setUp() {
		super.setUp();
		RoleContextHolder.set(Set.of());
		UserIDContextHolder.set(null);
	}

	protected void truncate(final String table) {
		this.entityManagerRepository.truncateTable(table);
	}

}
