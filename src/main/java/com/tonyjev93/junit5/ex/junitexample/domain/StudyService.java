package com.tonyjev93.junit5.ex.junitexample.domain;


import java.util.Optional;

public class StudyService {

    MemberService memberService;
    StudyRepository studyRepository;

    public StudyService(MemberService memberService, StudyRepository studyRepository) {
        assert memberService != null;
        assert studyRepository != null;

        this.memberService = memberService;
        this.studyRepository = studyRepository;
    }

    public Optional<Study> createNewStudy(Long l, Study study) {

        Optional<Member> member = memberService.findById(l);
        
        study.setOwner(member.get());

        return studyRepository.save(study);
    }
}
