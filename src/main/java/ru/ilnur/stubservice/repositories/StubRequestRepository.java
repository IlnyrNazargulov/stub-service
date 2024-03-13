package ru.ilnur.stubservice.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.ilnur.stubservice.model.StubRequest;

import java.util.List;

public interface StubRequestRepository extends CrudRepository<StubRequest, Integer> {
    @Query("select sr from StubRequest sr where (:tag is null or sr.tag = :tag) " +
            "order by sr.createdAt desc")
    List<StubRequest> findStubRequestByTag(String tag, Pageable pageable);
}
