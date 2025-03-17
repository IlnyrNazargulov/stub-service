package ru.ilnur.stubservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.ilnur.stubservice.model.*;
import ru.ilnur.stubservice.services.StubRequestService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class StubServerController {
    private final StubRequestService stubRequestService;
    private final ObjectMapper objectMapper;

    @PostMapping(value = "/stub-requests/{tag}")
    public ResponseEntity handleRequests(
            @Validated @RequestBody String body,
            @PathVariable String tag,
            @RequestParam(required = false) boolean returnException
    ) {
        if (returnException) {
            return ResponseEntity.notFound().build();
        }
        try {
            StubBodyLevel1 stubBodyLevel1 = objectMapper.readValue(body, StubBodyLevel1.class);
            List<ResultItem> resultItems = new ArrayList<>();
            for (Map.Entry<String, StubBodyLevel2> entry : stubBodyLevel1.getItems().entrySet()) {
                resultItems.add(ResultItem.getRandom(entry.getValue().getIdDok() != null ? entry.getValue().getIdDok() : entry.getValue().getIDMember()));
            }
            StubResponse response = new StubResponse(200, resultItems);

            stubRequestService.createStubRequest(tag, body, objectMapper.writeValueAsString(response));
            return ResponseEntity.ok(response);
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
}
