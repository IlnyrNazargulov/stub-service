package ru.ilnur.stubservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class StubBodyLevel2 {
    @JsonProperty("IdDok")
    private String IdDok;
    @JsonProperty("IDMember")
    private String IDMember;
}
