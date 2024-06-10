package ru.ilnur.stubservice.model;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class StubBodyLevel1 {
    @JsonProperty("IDrequest")
    private String IDrequest;
    @JsonAnySetter
    private Map<String, StubBodyLevel2> items;
}
