package cronos.alice.exception;

public class ExportFailedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ExportFailedException(final String message) {
		super(message);
	}
}
