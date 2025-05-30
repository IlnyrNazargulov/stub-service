package ru.ilnur.stubservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StubResponseService {
    private String stubResponse = "";

    public void setStubResponse(String stubResponse) {
        this.stubResponse = stubResponse;
    }

    public String getStubResponse() {
        return stubResponse;
    }
}
