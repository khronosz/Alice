package cronos.alice.exception;

public class UniqueUsernameException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UniqueUsernameException(final String message) {
		super(message);
	}
}
