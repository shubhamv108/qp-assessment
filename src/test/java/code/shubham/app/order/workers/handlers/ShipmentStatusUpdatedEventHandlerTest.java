package code.shubham.app.order.workers.handlers;

import code.shubham.app.TestAppConstants;
import code.shubham.app.order.dao.entities.Order;
import code.shubham.app.order.dao.entities.OrderItem;
import code.shubham.app.order.dao.entities.OrderItemStatus;
import code.shubham.app.order.dao.entities.OrderStatus;
import code.shubham.app.order.dao.repositories.OrderItemRepository;
import code.shubham.app.order.dao.repositories.OrderRepository;
import code.shubham.commons.TestCommonConstants;
import code.shubham.commons.models.Event;
import code.shubham.commons.utils.UUIDUtils;
import code.shubham.test.AbstractSpringBootTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Disabled
class ShipmentStatusUpdatedEventHandlerTest extends AbstractSpringBootTest {

	@Autowired
	private ShipmentStatusUpdatedEventHandler handler;

	@Autowired
	private OrderRepository repository;

	@Autowired
	private OrderItemRepository orderProductRepository;

	@BeforeEach
	public void setUp() {
		super.setUp();
		truncate("orders");
		truncate("order__products_mapping");
	}

	@AfterEach
	void tearDown() {
		truncate("orders");
		truncate("order__products_mapping");
	}

	@Test
	void handle_PREPARE_TO_DISPATCH_shipment_status() {
		final Order exisitingOrder = this.repository.save(Order.builder()
			.status(OrderStatus.CREATED)
			.clientUniqueReferenceId(TestAppConstants.ORDER_UNIQUE_REFERENCE_ID)
			.userId(TestCommonConstants.USER_ID)
			.customerId(TestAppConstants.CUSTOMER_ID)
			.customerType("DRIVER")
			.build());

		final OrderItem exisitingOrderProduct = this.orderProductRepository.save(OrderItem.builder()
			.status(OrderItemStatus.CREATED)
			.clientUniqueReferenceId(
					UUIDUtils.uuid5(TestAppConstants.ORDER_UNIQUE_REFERENCE_ID + "_" + TestAppConstants.INVENTORY_ID))
			.orderId(exisitingOrder.getId())
			.inventoryId(TestAppConstants.INVENTORY_ID)
			.quantity(1)
			.build());

		final Event event = null;
		// TestAppEventUtils.getShipmentStatusUpdatedEvent(ShipmentStatus.PREPARE_TO_DISPATCH,
		// exisitingOrder.getId());

		this.handler.handle(event);

		final var orders = this.repository.findAllByUserId(TestCommonConstants.USER_ID);
		final var orderProducts = this.orderProductRepository.findAllByOrderId(orders.get(0).getId());
		assertEquals(1, orders.size());
		assertEquals(1, orderProducts.size());
		assertEquals(TestCommonConstants.USER_ID, orders.get(0).getUserId());
		assertEquals(OrderStatus.CREATED, orders.get(0).getStatus());
		assertEquals(TestAppConstants.ORDER_UNIQUE_REFERENCE_ID, orders.get(0).getClientUniqueReferenceId());
		assertEquals(TestAppConstants.CUSTOMER_ID, orders.get(0).getCustomerId());
		assertEquals("DRIVER", orders.get(0).getCustomerType());
		assertEquals(orders.get(0).getId(), orderProducts.get(0).getOrderId());
		assertEquals(TestAppConstants.INVENTORY_ID, orderProducts.get(0).getInventoryId());
		assertEquals(OrderItemStatus.SHIPPED.name(), orderProducts.get(0).getStatus().name());
		assertEquals(1, orderProducts.get(0).getQuantity());
		assertEquals(UUIDUtils.uuid5(TestAppConstants.ORDER_UNIQUE_REFERENCE_ID + "_" + TestAppConstants.INVENTORY_ID),
				orderProducts.get(0).getClientUniqueReferenceId());
	}

	@Test
	void handle_DELIVERED_shipment_status() {
		final Order exisitingOrder = this.repository.save(Order.builder()
			.status(OrderStatus.CREATED)
			.clientUniqueReferenceId(TestAppConstants.ORDER_UNIQUE_REFERENCE_ID)
			.userId(TestCommonConstants.USER_ID)
			.customerId(TestAppConstants.CUSTOMER_ID)
			.customerType("DRIVER")
			.build());

		final OrderItem exisitingOrderProduct = this.orderProductRepository.save(OrderItem.builder()
			.status(OrderItemStatus.SHIPPED)
			.clientUniqueReferenceId(
					UUIDUtils.uuid5(TestAppConstants.ORDER_UNIQUE_REFERENCE_ID + "_" + TestAppConstants.INVENTORY_ID))
			.orderId(exisitingOrder.getId())
			.inventoryId(TestAppConstants.INVENTORY_ID)
			.quantity(1)
			.build());

		final Event event = null;
		// TestAppEventUtils.getShipmentStatusUpdatedEvent(ShipmentStatus.DELIVERED,
		// exisitingOrder.getId());

		this.handler.handle(event);

		final var orders = this.repository.findAllByUserId(TestCommonConstants.USER_ID);
		final var orderProducts = this.orderProductRepository.findAllByOrderId(orders.get(0).getId());
		assertEquals(1, orders.size());
		assertEquals(1, orderProducts.size());
		assertEquals(TestCommonConstants.USER_ID, orders.get(0).getUserId());
		assertEquals(OrderStatus.COMPLETED.name(), orders.get(0).getStatus().name());
		assertEquals(TestAppConstants.ORDER_UNIQUE_REFERENCE_ID, orders.get(0).getClientUniqueReferenceId());
		assertEquals(TestAppConstants.CUSTOMER_ID, orders.get(0).getCustomerId());
		assertEquals("DRIVER", orders.get(0).getCustomerType());
		assertEquals(orders.get(0).getId(), orderProducts.get(0).getOrderId());
		assertEquals(TestAppConstants.INVENTORY_ID, orderProducts.get(0).getInventoryId());
		assertEquals(OrderItemStatus.COMPLETED.name(), orderProducts.get(0).getStatus().name());
		assertEquals(1, orderProducts.get(0).getQuantity());
		assertEquals(UUIDUtils.uuid5(TestAppConstants.ORDER_UNIQUE_REFERENCE_ID + "_" + TestAppConstants.INVENTORY_ID),
				orderProducts.get(0).getClientUniqueReferenceId());
	}

}