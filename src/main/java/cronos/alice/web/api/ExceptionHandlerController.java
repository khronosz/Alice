package cronos.alice.web.api;

import java.util.Iterator;
import java.util.Objects;
import javax.validation.ConstraintViolationException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import cronos.alice.exception.UniqueDemandNameException;
import cronos.alice.exception.UniqueDemandsMibException;
import cronos.alice.exception.UniqueDemandsUserProjectException;
import cronos.alice.exception.UniqueEmailAddressException;
import cronos.alice.exception.ErrorMessage;
import cronos.alice.exception.ExportFailedException;
import cronos.alice.exception.IllegalDateException;
import cronos.alice.exception.InvalidPasswordFormatException;
import cronos.alice.exception.PasswordDoesNotMatchException;
import cronos.alice.exception.UniqueProjectsSapException;
import cronos.alice.exception.UniqueUsernameException;
import cronos.alice.exception.UtilizationTooMuchException;

@ControllerAdvice
public class ExceptionHandlerController {

	private static final Logger log = LogManager.getLogger(ExceptionHandlerController.class);

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorMessage> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
		ErrorMessage message = new ErrorMessage(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage());
		log.error(message.getStatusCode() + ": " + message.getStatus() + " - " + message.getMessage());
		return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(UnsupportedOperationException.class)
	public ResponseEntity<?> unsupportedOperationExceptionHandler(UnsupportedOperationException e) {
		ErrorMessage message = new ErrorMessage(HttpStatus.NOT_IMPLEMENTED.value(), HttpStatus.NOT_IMPLEMENTED.getReasonPhrase(), e.getMessage());
		log.error(message.getStatusCode() + ": " + message.getStatus() + " - " + message.getMessage());
		return new ResponseEntity<>("Function is not implemented yet!", HttpStatus.NOT_IMPLEMENTED);
	}

	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<?> usernameNotFoundExceptionHandler(UsernameNotFoundException e) {
		ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(), e.getMessage());
		log.error(message.getStatusCode() + ": " + message.getStatus() + " - " + message.getMessage());
		return new ResponseEntity<>(message.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<?> runtimeExceptionHandler(RuntimeException e) {
		ErrorMessage message = new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e.getMessage());
		log.error(message.getStatusCode() + ": " + message.getStatus() + " - " + message.getMessage());
		return new ResponseEntity<>(message.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(IllegalDateException.class)
	public ResponseEntity<ErrorMessage> illegalDateExceptionHandler(IllegalDateException e) {
		ErrorMessage message = new ErrorMessage(HttpStatus.NOT_ACCEPTABLE.value(), HttpStatus.NOT_ACCEPTABLE.getReasonPhrase(), e.getMessage());
		log.error(message.getStatusCode() + ": " + message.getStatus() + " - " + message.getMessage());
		return new ResponseEntity<>(message, HttpStatus.NOT_ACCEPTABLE);
	}

	@ExceptionHandler(PasswordDoesNotMatchException.class)
	public ResponseEntity<ErrorMessage> passwordDoesNotMatchExceptionHandler(PasswordDoesNotMatchException e) {
		ErrorMessage message = new ErrorMessage(HttpStatus.NOT_ACCEPTABLE.value(), HttpStatus.NOT_ACCEPTABLE.getReasonPhrase(), e.getMessage());
		log.error(message.getStatusCode() + ": " + message.getStatus() + " - " + message.getMessage());
		return new ResponseEntity<>(message, HttpStatus.NOT_ACCEPTABLE);
	}

	@ExceptionHandler(InvalidPasswordFormatException.class)
	public ResponseEntity<ErrorMessage> invalidPasswordFormatExceptionHandler(InvalidPasswordFormatException e) {
		ErrorMessage message = new ErrorMessage(HttpStatus.NOT_ACCEPTABLE.value(), HttpStatus.NOT_ACCEPTABLE.getReasonPhrase(), e.getMessage());
		log.error(message.getStatusCode() + ": " + message.getStatus() + " - " + message.getMessage());
		return new ResponseEntity<>(message, HttpStatus.NOT_ACCEPTABLE);
	}

	@ExceptionHandler(UtilizationTooMuchException.class)
	public ResponseEntity<ErrorMessage> utilizationTooMuchExceptionHandler(UtilizationTooMuchException e) {
		ErrorMessage message = new ErrorMessage(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), e.getMessage());
		log.error(message.getStatusCode() + ": " + message.getStatus() + " - " + message.getMessage());
		return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ExportFailedException.class)
	public ResponseEntity<ErrorMessage> exportFailedExceptionHandler(ExportFailedException e) {
		ErrorMessage message = new ErrorMessage(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), e.getMessage());
		log.error(message.getStatusCode() + ": " + message.getStatus() + " - " + message.getMessage());
		return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(UniqueEmailAddressException.class)
	public ResponseEntity<ErrorMessage> uniqueEmailAddressExceptionHandler(UniqueEmailAddressException e) {
		ErrorMessage message = new ErrorMessage(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), e.getMessage());
		log.error(message.getStatusCode() + ": " + message.getStatus() + " - " + message.getMessage());
		return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(UniqueUsernameException.class)
	public ResponseEntity<ErrorMessage> uniqueUsernameExceptionHandler(UniqueUsernameException e) {
		ErrorMessage message = new ErrorMessage(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), e.getMessage());
		log.error(message.getStatusCode() + ": " + message.getStatus() + " - " + message.getMessage());
		return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(UniqueDemandNameException.class)
	public ResponseEntity<ErrorMessage> uniqueDemandNameExceptionHandler(UniqueDemandNameException e) {
		ErrorMessage message = new ErrorMessage(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), e.getMessage());
		log.error(message.getStatusCode() + ": " + message.getStatus() + " - " + message.getMessage());
		return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(UniqueDemandsMibException.class)
	public ResponseEntity<ErrorMessage> uniqueDemandsMibExceptionHandler(UniqueDemandsMibException e) {
		ErrorMessage message = new ErrorMessage(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), e.getMessage());
		log.error(message.getStatusCode() + ": " + message.getStatus() + " - " + message.getMessage());
		return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(UniqueDemandsUserProjectException.class)
	public ResponseEntity<ErrorMessage> uniqueDemandsUserProjectExceptionHandler(UniqueDemandsUserProjectException e) {
		ErrorMessage message = new ErrorMessage(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), e.getMessage());
		log.error(message.getStatusCode() + ": " + message.getStatus() + " - " + message.getMessage());
		return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(UniqueProjectsSapException.class)
	public ResponseEntity<ErrorMessage> uniqueProjectsSapExceptionHandler(UniqueProjectsSapException e) {
		ErrorMessage message = new ErrorMessage(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), e.getMessage());
		log.error(message.getStatusCode() + ": " + message.getStatus() + " - " + message.getMessage());
		return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<ErrorMessage> dataIntegrityViolationExceptionHandler(DataIntegrityViolationException e) {
		ErrorMessage message = new ErrorMessage(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase());
		String msg = Objects.requireNonNull(e.getMessage()).substring(52);
		msg = msg.substring(0, msg.indexOf("]"));
		switch (msg) {
			case "UNIQUE_ROLE_NAME":
				message.setMessage("Duplicate Error! - Role name must be unique!");
				log.error(message.getStatusCode() + ": " + message.getStatus() + " - " + message.getMessage());
				return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
			case "UNIQUE_USER_ROLE":
				message.setMessage("Duplicate Error! - User already has this role!");
				log.error(message.getStatusCode() + ": " + message.getStatus() + " - " + message.getMessage());
				return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
			default:
				message.setMessage("Unknown SQL Data Integrity Violation Error!");
				log.error(message.getStatusCode() + ": " + message.getStatus() + " - " + message.getMessage());
				return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
		}
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<?> constraintViolationExceptionHandler(ConstraintViolationException e) {
		ErrorMessage message = new ErrorMessage(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase());
		Iterator violations = e.getConstraintViolations().iterator();
		if (violations.hasNext()) {
			ConstraintViolationImpl violation = (ConstraintViolationImpl) violations.next();
			message.setMessage(violation.getMessageTemplate());
			log.error(message.getStatusCode() + ": " + message.getStatus() + " - " + message.getMessage());
			return new ResponseEntity<>(message.getMessage(), HttpStatus.BAD_REQUEST);
		}
		message.setMessage("Unknown Validation Constraint Violation Error!");
		log.error(message.getStatusCode() + ": " + message.getStatus() + " - " + message.getMessage());
		return new ResponseEntity<>(message.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<?> authenticationExceptionHandler(AuthenticationException e) {
		ErrorMessage message = new ErrorMessage(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase(), e.getMessage());
		log.error(message.getStatusCode() + ": " + message.getStatus() + " - " + message.getMessage());
		return new ResponseEntity<>("Access denied!", HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> globalExceptionHandler(Exception e) {
		ErrorMessage message = new ErrorMessage(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), e.getMessage());
		log.error(message.getStatusCode() + ": " + message.getStatus() + " - " + message.getMessage());
		return new ResponseEntity<>("Unknown error!", HttpStatus.BAD_REQUEST);
	}
}
