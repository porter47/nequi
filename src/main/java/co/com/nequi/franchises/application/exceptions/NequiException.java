package co.com.nequi.franchises.application.exceptions;

import co.com.nequi.franchises.application.domain.MessagesEnum;
import lombok.Getter;

@Getter
public class NequiException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final MessagesEnum errorMessage;

	private final Throwable exception;

	public NequiException(MessagesEnum errorMessage) {
		super(errorMessage.getMessage());
		this.exception = null;
		this.errorMessage = errorMessage;
	}

	public NequiException(Throwable exception, MessagesEnum errorMessage) {
		super(errorMessage.getMessage());
		this.exception = exception;
		this.errorMessage = errorMessage;
	}
}