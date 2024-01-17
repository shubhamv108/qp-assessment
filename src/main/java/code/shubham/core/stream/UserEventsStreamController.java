package code.shubham.core.stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Date;

//@RestController
public class UserEventsStreamController {

	// @Autowired
	// public UserEventsStreamController(final UserEventSubscriber eventSubscriber) {
	// this.eventSubscriber = eventSubscriber;
	// }
	//
	// @GetMapping(path = "/stream-flux", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	// public Flux<String> streamFlux() {
	// return Flux.interval(Duration.ofSeconds(100)).map(sequence -> "Flux - " +
	// LocalTime.now().toString());
	// }
	//
	// @GetMapping("/stream-grocery-booking")
	// public Flux<ServerSentEvent<?>> streamEvents(@RequestParam("userId") String userId,
	// @RequestParam("timestamp") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
	// Date timestamp) {
	// return this.eventSubscriber.get()
	// .filter(event -> userId.equals(event.getUserId()) &&
	// timestamp.compareTo(event.getTimestamp()) == -1)
	// .map(event -> ServerSentEvent.<UserEvent>builder()
	// .id(event.getEventId())
	// .event(event.getType() + event.getName())
	// .data(event)
	// .build());
	// }

}
