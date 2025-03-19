package co.com.nequi.franchises.adapter.in.web;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import co.com.nequi.franchises.application.domain.MessagesEnum;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
@Getter
@Setter
public class MessageResponse<T> {

	private String code;

	private String message;

	private T content;

	public static <T> ResponseEntity<MessageResponse<T>> buildResponseMessage(T content, MessagesEnum responseMessage) {

		var message = new MessageResponse<T>();
		message.setCode(responseMessage.getCode());
		message.setMessage(responseMessage.getMessage());
		message.setContent(content);

		return ResponseEntity.status(HttpStatusCode.valueOf(responseMessage.getResponseCode())).body(message);
	}

	public static ResponseEntity<MessageResponse<Void>> buildResponseMessage(MessagesEnum responseMessage) {
		var message = new MessageResponse<Void>();
		message.setCode(responseMessage.getCode());
		message.setMessage(responseMessage.getMessage());

		return ResponseEntity.status(HttpStatusCode.valueOf(responseMessage.getResponseCode())).body(message);
	}
	
	public static ResponseEntity<Void> successNoContent(MessagesEnum responseMessage) {
		return ResponseEntity.status(HttpStatusCode.valueOf(responseMessage.getResponseCode())).build();
	}
}
