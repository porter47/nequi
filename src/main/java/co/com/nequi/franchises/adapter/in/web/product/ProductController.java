package co.com.nequi.franchises.adapter.in.web.product;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.nequi.franchises.adapter.in.web.MessageResponse;
import co.com.nequi.franchises.adapter.in.web.WebResourcesConstants;
import co.com.nequi.franchises.application.domain.MessagesEnum;
import co.com.nequi.franchises.application.domain.Product;
import co.com.nequi.franchises.application.ports.in.ProductUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(WebResourcesConstants.PRODUCT_RESOURCE)
@Slf4j
@RequiredArgsConstructor
class ProductController {

	private final ProductUseCase productUseCase;

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	Mono<ResponseEntity<MessageResponse<ProductOutMessage>>> save(@RequestBody ProductMessage product,
			@RequestHeader(name = WebResourcesConstants.BR_HEADER_NAME) String branchId) {

		log.info("Product body arrive to be saved ... ");

		var domain = inputToDomain(product);
		domain.setBranchId(branchId);

		var response = productUseCase.save(domain);

		return response.map(resp -> MessageResponse.buildResponseMessage(domainToOutput(resp), MessagesEnum.CREATED));
	}

	@PatchMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	Mono<ResponseEntity<MessageResponse<ProductOutMessage>>> update(@RequestBody ProductMessage product,
			@RequestHeader(name = WebResourcesConstants.PR_HEADER_NAME) String productId) {

		log.info("Product body arrive to be updated ... ");

		var domain = inputToDomain(product);

		domain.setId(productId);

		var response = productUseCase.update(domain);

		return response.map(resp -> MessageResponse.buildResponseMessage(domainToOutput(resp), MessagesEnum.SUCCESS));
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	Mono<ResponseEntity<MessageResponse<List<Product>>>> getMaxStock(
			@RequestHeader(name = WebResourcesConstants.FR_HEADER_NAME) String franchiseId) {

		log.info("Start query products max stock ... ");

		var response = productUseCase.findMaxStock(franchiseId);

		return response.map(resp -> MessageResponse.buildResponseMessage(resp, MessagesEnum.SUCCESS));
	}

	@DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	Mono<ResponseEntity<Void>> delete(@RequestHeader(name = WebResourcesConstants.PR_HEADER_NAME) String productId) {

		log.info("Product to be deleted with id {} ... ", productId);

		var response = productUseCase.delete(productId);

		return response.thenReturn(MessageResponse.successNoContent(MessagesEnum.SUCCESS_NO_CONTENT));
	}

	private Product inputToDomain(ProductMessage input) {
		var product = new Product();

		product.setName(input.getName());
		product.setStock(input.getStock());

		return product;
	}

	private ProductOutMessage domainToOutput(Product domain) {
		var product = new ProductOutMessage();

		product.setId(domain.getId());
		product.setName(domain.getName());
		product.setStock(domain.getStock());
		product.setBranchName(domain.getBranchName());
		return product;
	}
}