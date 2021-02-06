package cronos.alice.exception;

public class ResourceNotFoundException extends RuntimeException {

	private static final Long serialVersionUID = 1L;

	public ResourceNotFoundException(final String message) {
		super(message);
	}
}
