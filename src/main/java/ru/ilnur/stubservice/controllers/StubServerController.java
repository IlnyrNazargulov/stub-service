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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;
import ru.ilnur.stubservice.model.StubRequest;
import ru.ilnur.stubservice.services.StubRequestService;
import ru.ilnur.stubservice.services.StubResponseService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
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


    @PostMapping(value = "/stub-requests/**", consumes = {MediaType.APPLICATION_JSON_VALUE})
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
            stubRequestService.createStubRequest(tag, body, stubResponseService.getStubResponse());
            return ResponseEntity.status(stubResponseService.getResponseCode()).body(stubResponseService.getStubResponse());
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        stubRequestService.createStubRequest(tag, body);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/stub-requests/multipart-form-data/**", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity handleRequests(
            @RequestParam(required = false) boolean returnException,
            @RequestParam("file") MultipartFile multipartFile,
            HttpServletRequest request
    ) {
        String tag = getWildcardParam(request);
        if (returnException) {
            return ResponseEntity.notFound().build();
        }
        StringBuilder multipartFileSb = new StringBuilder();
        if (multipartFile != null) {
            multipartFileSb.append("multipartFile with name: ").append(multipartFile.getName())
                    .append(", original name: ").append(multipartFile.getOriginalFilename()).append("\n");
            try (BufferedReader br = new BufferedReader(new InputStreamReader(multipartFile.getInputStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = br.readLine()) != null) {
                    multipartFileSb.append(line).append("\n");
                }
            }
            catch (IOException e) {
                multipartFileSb.append("\nError at read multipart file: ").append(multipartFile.getName()).append("\n");
                log.error("Error at read multipart file", e);
            }
        }
        String body = multipartFileSb.toString();
        try {
            stubRequestService.createStubRequest(tag, body, stubResponseService.getStubResponse());
            return ResponseEntity.status(stubResponseService.getResponseCode()).body(stubResponseService.getStubResponse());
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

    @PostMapping(value = "/stub-code/")
    public ResponseEntity updateStubResponse(
            @RequestBody Integer code
    ) {
        stubResponseService.setResponseCode(code);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/stub-response/")
    public ResponseEntity getStubResponse() {
        return ResponseEntity.ok(stubResponseService.getStubResponse());
    }

    @GetMapping(value = "/stub-code/")
    public ResponseEntity getStubCode() {
        return ResponseEntity.ok(stubResponseService.getResponseCode());
    }


    private String getWildcardParam(HttpServletRequest request) {
        String patternAttribute = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        String mappingAttribute = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        return this.pathMatcher.extractPathWithinPattern(patternAttribute, mappingAttribute);
    }
}
