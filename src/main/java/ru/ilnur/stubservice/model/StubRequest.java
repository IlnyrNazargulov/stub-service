package ru.ilnur.stubservice.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Getter
@Table(name = "stub_request")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StubRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, updatable = false)
    private String value;
    @Column(nullable = false, updatable = false)
    private String tag;
    @Column(updatable = false)
    private String response;
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    public StubRequest(String value, String tag, String response, Instant createdAt) {
        this.value = value;
        this.tag = tag;
        this.response = response;
        this.createdAt = createdAt;
    }
}
