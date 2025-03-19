package co.com.nequi.franchises.adapter.out.persistence.dynamodb;

import org.springframework.util.StringUtils;

import co.com.nequi.franchises.application.domain.DataSet;

public class DynamoSeparatorUtil {

	public static final String SEPARATOR = "#";

	private DynamoSeparatorUtil() {
	}

	public static DataSet<String, String, String> separateKeys(String key) {

		if (!StringUtils.hasText(key)) {
			return new DataSet<>();
		}

		var split = key.split(SEPARATOR);

		var lengthCount = 3;

		if (split.length == lengthCount--) {
			return new DataSet<>(split[0], split[1], split[2]);
		}

		if (split.length == lengthCount) {
			return new DataSet<>(split[0], split[1], null);
		}

		return new DataSet<>(split[0], null, null);
	}
}
