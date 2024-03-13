package ru.ilnur.stubservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.ilnur.stubservice.model.StubRequest;
import ru.ilnur.stubservice.services.StubRequestService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class StubServerController {
    private final StubRequestService stubRequestService;

    @PostMapping(value = "/stub-requests/{tag}/")
    public ResponseEntity handleRequests(
            @Validated @RequestBody String body,
            @PathVariable String tag,
            @RequestParam(required = false) boolean returnException
    ) {
        if (returnException) {
            return ResponseEntity.notFound().build();
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
