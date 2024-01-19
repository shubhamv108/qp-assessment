package code.shubham.commons.validators;

public interface IValidator<OBJECT> {

	String IS_EMPTY = "%s is empty.";

	String MUST_NOT_BE_EMPTY = "%s must not be empty.";

	String MUST_BE_VALID_ID = "%s must be valid id.";

	String MUST_NOT_BE_LESS_THAN_ZERO = "%s  must not be empty and must not be less than 0";

	IValidator<OBJECT> validate(OBJECT object);

	IValidator<OBJECT> validateOrThrowException(OBJECT object);

	boolean hasMessages();

	java.util.Map<String, java.util.Collection<String>> getResult();

}