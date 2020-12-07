package com.tonyjev93.junit5.ex.junitexample.domain;

import java.util.Optional;

public interface MemberService {
    Optional<Member> findById(Long l);

    void validate(long l);

    void notify(Study newStudy);

    void notify(Member member);
}
