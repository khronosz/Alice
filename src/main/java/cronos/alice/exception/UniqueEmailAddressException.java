package cronos.alice.exception;

public class UniqueEmailAddressException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UniqueEmailAddressException(final String message) {
		super(message);
	}
}
