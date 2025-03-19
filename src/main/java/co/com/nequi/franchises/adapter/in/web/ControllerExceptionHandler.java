package co.com.nequi.franchises.adapter.in.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import co.com.nequi.franchises.application.domain.MessagesEnum;
import co.com.nequi.franchises.application.exceptions.NequiException;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@RestControllerAdvice
@Slf4j
class ControllerExceptionHandler {

	@ExceptionHandler(Exception.class)
	Mono<ResponseEntity<MessageResponse<Void>>> handleException(Exception ex) {
		log.error(ex.getMessage(), ex);

		return Mono.just(MessageResponse.buildResponseMessage(MessagesEnum.UNKNOWN_ERROR));
	}

	@ExceptionHandler(NequiException.class)
	Mono<ResponseEntity<MessageResponse<Void>>> handleQueueException(NequiException ex) {
		log.error(ex.getMessage(), ex);

		return Mono.just(MessageResponse.buildResponseMessage(ex.getErrorMessage()));
	}
}
