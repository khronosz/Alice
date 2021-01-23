package cronos.alice.exception;

public class InvalidPasswordFormatException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidPasswordFormatException(final String message) {
		super(message);
	}
}
