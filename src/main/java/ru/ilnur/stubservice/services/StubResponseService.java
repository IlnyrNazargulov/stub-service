package ru.ilnur.stubservice.services;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@RequiredArgsConstructor
public class StubResponseService {
    private Integer responseCode = 200;
    private String stubResponse = "";
}
