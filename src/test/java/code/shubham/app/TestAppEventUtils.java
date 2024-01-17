package code.shubham.app;

import code.shubham.app.constants.EventName;
import code.shubham.app.constants.EventType;
import code.shubham.app.order.dao.entities.Order;
import code.shubham.app.order.dao.entities.OrderItemStatus;
import code.shubham.app.ordermodels.CreateOrderCommand;
import code.shubham.app.ordermodels.OrderDTO;
import code.shubham.app.ordermodels.OrderEventData;
import code.shubham.app.ordermodels.OrderItemDTO;
import code.shubham.commons.TestCommonConstants;
import code.shubham.commons.contexts.CorrelationIDContext;
import code.shubham.commons.models.Event;
import code.shubham.commons.utils.JsonUtils;
import code.shubham.commons.utils.UUIDUtils;

import java.util.Date;
import java.util.List;

public class TestAppEventUtils {

	public static Event getOrderEvent(final Order order, final EventName eventName) {
		return Event.builder()
			.eventName(eventName.name())
			.eventType(EventType.ORDER.name())
			.data(JsonUtils.get(OrderEventData.builder()
				.order(OrderDTO.builder()
					.orderId(order.getId())
					.status(order.getStatus().name())
					.customerId(order.getCustomerId())
					.customerType(order.getCustomerType())
					.userId(order.getUserId())
					.uniqueReferenceId(order.getUniqueReferenceId())
					.build())
				.products(List.of(OrderItemDTO.builder()
					.productId(TestAppConstants.PRODUCT_ID)
					.quantity(1)
					.status(OrderItemStatus.CREATED.name())
					.clientReferenceId(UUIDUtils
						.uuid5(TestAppConstants.ORDER_UNIQUE_REFERENCE_ID + "_" + TestAppConstants.PRODUCT_ID))
					.build()))
				.build()))
			.createdAt(new Date())
			.userId(order.getUserId())
			.uniqueReferenceId(order.getUniqueReferenceId())
			.correlationId(CorrelationIDContext.get())
			.build();
	}

	public static Event getCreateOrderCommandEvent() {
		return Event.builder()
			.eventName(EventName.CreateOrderCommand.name())
			.eventType(EventType.ORDER.name())
			.data(JsonUtils.get(CreateOrderCommand.builder()
				.products(List.of(OrderItemDTO.builder()
					.productId(TestAppConstants.PRODUCT_ID)
					.quantity(1)
					.clientReferenceId(UUIDUtils
						.uuid5(TestAppConstants.ORDER_UNIQUE_REFERENCE_ID + "_" + TestAppConstants.PRODUCT_ID))
					.build()))
				.userId(TestCommonConstants.USER_ID)
				.customerId(TestAppConstants.CUSTOMER_ID)
				.customerType("BUYER")
				.clientReferenceId(TestAppConstants.ORDER_UNIQUE_REFERENCE_ID)
				.build()))
			.uniqueReferenceId(TestAppConstants.ORDER_UNIQUE_REFERENCE_ID)
			.userId(TestCommonConstants.USER_ID)
			.createdAt(new Date())
			.correlationId(CorrelationIDContext.get())
			.build();
	}

}
