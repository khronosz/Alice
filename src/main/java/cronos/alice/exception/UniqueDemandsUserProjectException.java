package cronos.alice.exception;

public class UniqueDemandsUserProjectException extends RuntimeException {

	private static final Long serialVersionUID = 1L;

	public UniqueDemandsUserProjectException(final String message) {
		super(message);
	}
}
