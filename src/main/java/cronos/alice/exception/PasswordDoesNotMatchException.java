package cronos.alice.exception;

public class PasswordDoesNotMatchException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public PasswordDoesNotMatchException(final String message) {
		super(message);
	}
}
