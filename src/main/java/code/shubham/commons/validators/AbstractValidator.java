package code.shubham.commons.validators;

import code.shubham.commons.utils.StringUtils;

import java.util.Collection;
import java.util.Map;

public abstract class AbstractValidator<OBJECT> implements IValidator<OBJECT> {

	private final Collection<Map<String, Collection<String>>> errorMessagesList = new java.util.ArrayList<>();

	private Map<String, Collection<String>> messages = new java.util.LinkedHashMap<>();

	public void next() {
		this.errorMessagesList.add(messages);
		this.messages = new java.util.LinkedHashMap<>();
	}

	@SuppressWarnings("all")
	protected boolean putMessage(final String messageKey, final String messageValueFormat) {
		return this.putMessage(messageKey, messageValueFormat, null);
	}

	protected boolean putMessage(final String messageKey, final String messageValueFormat,
			final Object... messageValueArguments) {
		String messageValue = String.format(messageValueFormat, messageValueArguments);
		Collection<String> messagesValues = this.messages.get(messageKey);
		if (messagesValues == null)
			this.messages.put(messageKey, messagesValues = new java.util.ArrayList<>());
		return messagesValues.add(messageValue);
	}

	protected boolean putMessages(final String messageKey, final Collection<String> messageValue) {
		Collection<String> messagesValues = this.messages.get(messageKey);
		if (messagesValues == null)
			this.messages.put(messageKey, messagesValues = new java.util.ArrayList<>());
		return messagesValues.addAll(messageValue);
	}

	protected boolean isNotValidId(final String id) {
		return StringUtils.isEmpty(id) || id.length() < 36;
	}

	protected boolean isUpdatable(final Object o) {
		return o != null;
	}

	protected boolean isInvalidNumber(final Number n) {
		return n == null;
	}

	protected boolean isInvalidPrice(final Double price) {
		return this.isInvalidNumber(price) || price < 0.0;
	}

	protected boolean isInvalidQuantity(final Integer quantity) {
		return this.isInvalidNumber(quantity) || quantity < 0;
	}

	@Override
	public boolean hasMessages() {
		return !this.getMessages().isEmpty();
	}

	@Override
	public Map<String, Collection<String>> getResult() {
		Map<String, Collection<String>> copy = new java.util.LinkedHashMap<>();
		this.messages.forEach((k, v) -> copy.put(k, new java.util.ArrayList<String>(v)));
		return copy;
	}

	private Map<String, Collection<String>> getMessages() {
		return this.messages;
	}

}
