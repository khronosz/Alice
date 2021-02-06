package cronos.alice.exception;

public class EmailAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EmailAlreadyExistsException(final String message) {
		super(message);
	}
}
