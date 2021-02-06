package cronos.alice.exception;

public class UniqueDemandNameException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UniqueDemandNameException(final String message) {
		super(message);
	}
}
