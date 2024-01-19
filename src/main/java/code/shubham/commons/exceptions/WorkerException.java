package code.shubham.commons.exceptions;

public class WorkerException extends RuntimeException {

	@SuppressWarnings("all")
	public WorkerException(final String message, final String... args) {
		super(String.format(message, args));
	}

}
