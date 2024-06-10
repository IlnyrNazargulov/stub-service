package ru.ilnur.stubservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import ru.ilnur.stubservice.model.StubRequest;
import ru.ilnur.stubservice.repositories.StubRequestRepository;

import java.time.Instant;
import java.util.List;

@Component
@RequiredArgsConstructor
public class StubRequestService {
    private final StubRequestRepository stubRequestRepository;

    public void createStubRequest(String tag, String value) {
        Instant now = Instant.now();
        stubRequestRepository.save(new StubRequest(value, tag, null, now));
    }

    public void createStubRequest(String tag, String value, String response) {
        Instant now = Instant.now();
        stubRequestRepository.save(new StubRequest(value, tag, response, now));
    }

    public List<StubRequest> getStubRequests(String tag, Pageable pageable) {
        return stubRequestRepository.findStubRequestByTag(tag, pageable);
    }

    public void deleteStubRequests(List<Integer> ids) {
        stubRequestRepository.deleteAllById(ids);
    }
}
