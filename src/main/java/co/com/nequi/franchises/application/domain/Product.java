package co.com.nequi.franchises.application.domain;

import org.springframework.util.StringUtils;

import co.com.nequi.franchises.application.exceptions.NequiException;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {

	private String id;

	private String name;

	private Double stock;

	private String branchId;

	private String branchName;

	public void validate() {
		if (!StringUtils.hasText(name)) {
			throw new NequiException(MessagesEnum.PR_NAME_MANDATORY);
		}

		if (!StringUtils.hasText(branchId)) {
			throw new NequiException(MessagesEnum.BR_ID_MANDATORY);
		}

		if (stock == null) {
			stock = 0D;
		}
	}

	public void validateUpdate() {
		if (!StringUtils.hasText(id)) {
			throw new NequiException(MessagesEnum.PR_ID_MANDATORY);
		}
	}

	public void nameToUpper() {
		if (name != null) {
			name = name.toUpperCase();
		}
	}
}
