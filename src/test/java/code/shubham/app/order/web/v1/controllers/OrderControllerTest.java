package code.shubham.app.order.web.v1.controllers;

import code.shubham.app.TestAppConstants;
import code.shubham.app.order.dao.entities.Order;
import code.shubham.app.order.dao.entities.OrderStatus;
import code.shubham.app.order.dao.repositories.OrderRepository;
import code.shubham.commons.TestCommonConstants;
import code.shubham.commons.contexts.UserIDContextHolder;
import code.shubham.test.AbstractSpringBootMVCTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class OrderControllerTest extends AbstractSpringBootMVCTest {

	private final String baseURL = "/v1/orders";

	@Autowired
	private OrderRepository repository;

	@BeforeEach
	public void setUp() {
		super.setUp();
		truncate("orders");
		UserIDContextHolder.set(TestCommonConstants.USER_ID);
	}

	@AfterEach
	void tearDown() {
		truncate("orders");
	}

	@Test
	void getAllOrders_with_invalid_userId() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.get(this.baseURL)
				.contentType(MediaType.APPLICATION_JSON)
				.param("userId", "INVALID_USER_ID")
				.content(as(null)))
			.andExpect(status().is(400))
			.andExpect(
					content().json("{\n" + "    \"statusCode\": 400,\n" + "    \"data\": null,\n" + "    \"error\": [\n"
							+ "        {\n" + "            \"userId\": [\n" + "                \"User with userId: "
							+ "INVALID_USER_ID" + " not allowed to perform the operation\"\n" + "            ]\n"
							+ "        }\n" + "    ]\n" + "}"));
	}

	@Test
	void getAllOrders_Success() throws Exception {
		final Order created = this.repository.save(Order.builder()
			.customerId(TestCommonConstants.USER_ID)
			.customerType("DRIVER")
			.uniqueReferenceId(TestAppConstants.ORDER_UNIQUE_REFERENCE_ID)
			.status(OrderStatus.CREATED)
			.userId(TestCommonConstants.USER_ID)
			.build());

		this.mockMvc
			.perform(MockMvcRequestBuilders.get(this.baseURL)
				.contentType(MediaType.APPLICATION_JSON)
				.param("userId", TestCommonConstants.USER_ID)
				.content(as(null)))
			.andExpect(status().is(302))
			.andExpect(content().json("{\n" + "    \"statusCode\": 302,\n" + "    \"data\": {\n"
					+ "        \"orders\": [\n" + "            {\n" + "                \"orderId\": \""
					+ created.getId() + "\",\n" + "                \"userId\": \"" + TestCommonConstants.USER_ID
					+ "\",\n" + "                \"status\": \"CREATED\"\n" + "            }\n" + "        ]\n"
					+ "    },\n" + "    \"error\": null\n" + "}"));
	}

}