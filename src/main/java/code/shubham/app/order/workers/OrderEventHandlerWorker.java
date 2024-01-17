package code.shubham.app.order.workers;

import code.shubham.commons.workers.AbstractEventHandlerWorker;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@ConditionalOnProperty(prefix = "service", name = "module", havingValue = "worker")
public class OrderEventHandlerWorker extends AbstractEventHandlerWorker {

	@Value("${order.worker.event.filters.eventname}")
	private Set<String> eventNameFilters;

	@Value("${order.worker.event.failure.kafka.topic.name}")
	private String failureTopic;

	@Override
	@KafkaListener(topics = { "${order.worker.shipment.event.kafka.topic.name}",
			"${order.worker.command.event.kafka.topic.name}" }, groupId = "${order.worker.kafka.group.id}")
	public void onReceive(final ConsumerRecord<?, ?> consumerRecord, final Acknowledgment acknowledgment) {
		this.work(consumerRecord, acknowledgment);
	}

	@Override
	protected Set<String> getEventNameFilters() {
		return this.eventNameFilters;
	}

	@Override
	protected String getFailureTopic() {
		return failureTopic;
	}

}
