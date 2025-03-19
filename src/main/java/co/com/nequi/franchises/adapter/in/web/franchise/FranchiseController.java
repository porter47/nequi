package co.com.nequi.franchises.adapter.in.web.franchise;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.nequi.franchises.adapter.in.web.MessageResponse;
import co.com.nequi.franchises.adapter.in.web.WebResourcesConstants;
import co.com.nequi.franchises.application.domain.Franchise;
import co.com.nequi.franchises.application.domain.MessagesEnum;
import co.com.nequi.franchises.application.ports.in.FranchiseUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(WebResourcesConstants.FRANCHISE_RESOURCE)
@Slf4j
@RequiredArgsConstructor
class FranchiseController {

	private final FranchiseUseCase franchiseUseCase;

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	Mono<ResponseEntity<MessageResponse<FranchiseOutMessage>>> save(@RequestBody FranchiseMessage franchise) {

		log.info("Franchise body arrive to be saved ... ");

		var response = franchiseUseCase.save(new Franchise(null, franchise.getName()));

		return response.map(resp -> MessageResponse
				.buildResponseMessage(new FranchiseOutMessage(resp.getId(), resp.getName()), MessagesEnum.CREATED));
	}

	@PatchMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	Mono<ResponseEntity<MessageResponse<FranchiseOutMessage>>> update(@RequestBody FranchiseMessage franchise,
			@RequestHeader(name = WebResourcesConstants.FR_HEADER_NAME) String franchiseId) {

		log.info("Franchise body arrive to be updated ... ");

		var response = franchiseUseCase.update(new Franchise(franchiseId, franchise.getName()));

		return response.map(resp -> MessageResponse
				.buildResponseMessage(new FranchiseOutMessage(resp.getId(), resp.getName()), MessagesEnum.SUCCESS));
	}
}