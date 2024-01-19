package code.shubham.commons.exceptions;

public abstract class AbstractAppException extends RuntimeException {

	@SuppressWarnings("all")
	public AbstractAppException(final String message, String... args) {
		super(String.format(message, args));
	}

}