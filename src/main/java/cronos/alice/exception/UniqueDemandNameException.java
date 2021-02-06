package cronos.alice.exception;

public class DemandNameAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DemandNameAlreadyExistsException(final String message) {
		super(message);
	}
}
