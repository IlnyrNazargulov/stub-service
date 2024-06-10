package ru.ilnur.stubservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class StubResponse {
    @JsonProperty("КодОтвета")
    private final Integer codeStatus;
    @JsonProperty("РезультатОбработки")
    private final List<ResultItem> items;
}
