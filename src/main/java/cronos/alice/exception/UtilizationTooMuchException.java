package cronos.alice.exception;

public class UtilizationTooMuchException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UtilizationTooMuchException(final String message) {
		super(message);
	}
}
