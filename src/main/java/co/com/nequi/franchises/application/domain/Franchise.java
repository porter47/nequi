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
public class Franchise {

	private String id;

	private String name;

	public void validate() {
		if (!StringUtils.hasText(name)) {
			throw new NequiException(MessagesEnum.FR_NAME_MANDATORY);
		}
	}
}
