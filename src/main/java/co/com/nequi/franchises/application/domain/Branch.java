package co.com.nequi.franchises.application.domain;

import org.springframework.util.StringUtils;

import co.com.nequi.franchises.application.exceptions.NequiException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Branch {

	private String id;

	private String name;

	private String franchiseId;

	private String franchiseName;

	public void validate() {
		if (!StringUtils.hasText(name)) {
			throw new NequiException(MessagesEnum.BR_NAME_MANDATORY);
		}

		if (!StringUtils.hasText(franchiseId)) {
			throw new NequiException(MessagesEnum.FR_ID_MANDATORY);
		}
	}
	
	public void validateForUpdate() {
		if (!StringUtils.hasText(name)) {
			throw new NequiException(MessagesEnum.BR_NAME_MANDATORY);
		}

		if (!StringUtils.hasText(id)) {
			throw new NequiException(MessagesEnum.BR_ID_MANDATORY);
		}
	}
}
