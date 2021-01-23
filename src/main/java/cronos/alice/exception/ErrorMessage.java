package cronos.alice.exception;

public class ErrorMessage {

	private int statusCode;
	private String status;
	private String message;

	public ErrorMessage(final int statusCode, final String status) {
		this.statusCode = statusCode;
		this.status = status;
	}

	public ErrorMessage(final int statusCode, final String status, final String message) {
		this.statusCode = statusCode;
		this.status = status;
		this.message = message;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(final int statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(final String message) {
		this.message = message;
	}
}
