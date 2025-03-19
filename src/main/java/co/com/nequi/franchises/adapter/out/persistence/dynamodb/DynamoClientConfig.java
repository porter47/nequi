package co.com.nequi.franchises.adapter.out.persistence.dynamodb;

import org.springframework.context.annotation.Bean;

import co.com.nequi.franchises.infrastructure.annotations.Module;
import software.amazon.awssdk.auth.credentials.ContainerCredentialsProvider;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.core.SdkSystemSetting;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@Module
public class DynamoClientConfig {
	
	public static final String TABLE_NAME = "FRANCHISES";

	@Bean
	public DynamoDbEnhancedClient dynamoDbEnhancedAsyncClient() {
		// TODO inject the region
		var client = DynamoDbClient.builder().region(Region.US_EAST_1)
				.credentialsProvider(SdkSystemSetting.AWS_CONTAINER_CREDENTIALS_FULL_URI.getStringValue().isPresent()
						? ContainerCredentialsProvider.builder().build()
						: EnvironmentVariableCredentialsProvider.create())
				.build();

		return DynamoDbEnhancedClient.builder().dynamoDbClient(client).build();
	}
}
