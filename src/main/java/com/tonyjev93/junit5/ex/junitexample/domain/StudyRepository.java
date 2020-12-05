package com.tonyjev93.junit5.ex.junitexample.domain;

import java.util.Optional;

public interface StudyRepository {
    Optional<Study> save(Study study);
}
