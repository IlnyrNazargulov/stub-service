package ru.ilnur.stubservice.controllers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.PathMatcher;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.HandlerMapping;
import ru.ilnur.stubservice.model.StubRequest;
import ru.ilnur.stubservice.services.StubRequestService;
import ru.ilnur.stubservice.services.StubResponseService;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class StubServerController {
    private final StubResponseService stubResponseService;
    private final StubRequestService stubRequestService;
    //    private final ObjectMapper objectMapper;
    private final PathMatcher pathMatcher;


    @PostMapping(value = "/stub-requests/**")
    public ResponseEntity handleRequests(
            @Validated @RequestBody String body,
            @RequestParam(required = false) boolean returnException,
            HttpServletRequest request
    ) {
        String tag = getWildcardParam(request);
        if (returnException) {
            return ResponseEntity.notFound().build();
        }
        try {
//            StubBodyLevel1 stubBodyLevel1 = objectMapper.readValue(body, StubBodyLevel1.class);
//            List<ResultItem> resultItems = new ArrayList<>();
//            for (Map.Entry<String, StubBodyLevel2> entry : stubBodyLevel1.getItems().entrySet()) {
//                resultItems.add(ResultItem.getRandom(entry.getValue().getIdDok() != null ? entry.getValue().getIdDok() : entry.getValue().getIDMember()));
//            }
//            StubResponse response = new StubResponse(200, resultItems);
//
//            stubRequestService.createStubRequest(tag, body, objectMapper.writeValueAsString(response));
            return ResponseEntity.ok(stubResponseService.getStubResponse());
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        stubRequestService.createStubRequest(tag, body);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/stub-requests/")
    public ResponseEntity<List<StubRequest>> getStubRequests(
            Pageable pageable,
            @RequestParam(required = false) String tag
    ) {
        List<StubRequest> stubRequests = stubRequestService.getStubRequests(tag, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(stubRequests);
    }

    @DeleteMapping("/stub-requests/")
    public ResponseEntity<StubRequest> deleteStubRequests(
            @RequestParam List<Integer> ids
    ) {
        stubRequestService.deleteStubRequests(ids);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping(value = "/stub-response/")
    public ResponseEntity updateStubResponse(
            @RequestBody String body
    ) {
        stubResponseService.setStubResponse(body);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/stub-response/")
    public ResponseEntity getStubResponse(
            @RequestBody String body
    ) {
        return ResponseEntity.ok(stubResponseService.getStubResponse());
    }


    private String getWildcardParam(HttpServletRequest request) {
        String patternAttribute = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        String mappingAttribute = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        return this.pathMatcher.extractPathWithinPattern(patternAttribute, mappingAttribute);
    }
}
