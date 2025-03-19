package co.com.nequi.franchises.adapter.in.web.branch;

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
import co.com.nequi.franchises.application.domain.Branch;
import co.com.nequi.franchises.application.domain.MessagesEnum;
import co.com.nequi.franchises.application.ports.in.BranchUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(WebResourcesConstants.BRANCH_RESOURCE)
@Slf4j
@RequiredArgsConstructor
class BranchController {

	private final BranchUseCase branchUseCase;

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	Mono<ResponseEntity<MessageResponse<BranchOutMessage>>> save(@RequestBody BranchMessage branch,
			@RequestHeader(name = WebResourcesConstants.FR_HEADER_NAME) String franchiseId) {

		log.info("Branch body arrive to be saved ... ");

		var response = branchUseCase.save(new Branch(null, branch.getName(), franchiseId, null));

		return response.map(resp -> MessageResponse.buildResponseMessage(
				new BranchOutMessage(resp.getId(), resp.getName(), resp.getFranchiseId(), resp.getFranchiseName()),
				MessagesEnum.CREATED));
	}

	@PatchMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	Mono<ResponseEntity<MessageResponse<BranchOutMessage>>> update(@RequestBody BranchMessage branch,
			@RequestHeader(name = WebResourcesConstants.BR_HEADER_NAME) String branchId) {

		log.info("Franchise body arrive to be updated ... ");

		var response = branchUseCase.update(new Branch(branchId, branch.getName(), null, null));

		return response.map(resp -> MessageResponse.buildResponseMessage(
				new BranchOutMessage(resp.getId(), resp.getName(), resp.getFranchiseId(), resp.getFranchiseName()),
				MessagesEnum.SUCCESS));
	}
}