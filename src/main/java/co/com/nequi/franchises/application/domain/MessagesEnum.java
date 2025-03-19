package co.com.nequi.franchises.application.domain;

import lombok.Getter;

public enum MessagesEnum {

	SUCCESS(200, "nequi-fr-0200", null), 
	CREATED(201, "nequi-fr-0201", null), 
	SUCCESS_NO_CONTENT(204, null, null),
	FR_NAME_MANDATORY(400, "nequi-fr-0400", "The franchise name is mandatory."),
	BR_NAME_MANDATORY(400, "nequi-fr-0401", "The branch name is mandatory."),
	FR_ID_MANDATORY(400, "nequi-fr-0402", "The franchise id is mandatory."),
	PR_NAME_MANDATORY(400, "nequi-fr-0403", "The product name is mandatory."),
	BR_ID_MANDATORY(400, "nequi-fr-0405", "The branch id is mandatory."),
	PR_ID_MANDATORY(400, "nequi-fr-0407", "The product id is mandatory."),
	FR_NOT_FOUND(404, "nequi-fr-0404", "The franchise was not found."),
	BR_NOT_FOUND(404, "nequi-fr-0406", "The branch was not found."),
	PR_NOT_FOUND(404, "nequi-fr-0408", "The product was not found."),
	UNKNOWN_ERROR(500, "nequi-fr-0500", "An unknown error ocurred.");

	@Getter
	private Integer responseCode;

	@Getter
	private String code;

	@Getter
	private String message;

	MessagesEnum(Integer responseCode, String code, String message) {
		this.responseCode = responseCode;
		this.code = code;
		this.message = message;
	}
}
