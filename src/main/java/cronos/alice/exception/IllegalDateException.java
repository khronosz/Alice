package cronos.alice.exception;

public class IllegalDateException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public IllegalDateException(final String message) {
		super(message);
	}
}
