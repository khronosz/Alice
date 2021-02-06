package cronos.alice.exception;

public class UniqueRoleNameException extends RuntimeException {

	private static final Long serialVersionUID = 1L;

	public UniqueRoleNameException(final String message) {
		super(message);
	}
}
