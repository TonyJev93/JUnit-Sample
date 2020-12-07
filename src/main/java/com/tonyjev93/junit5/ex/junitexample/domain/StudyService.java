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
//        memberService.validate(l);
        Optional<Member> member = memberService.findById(l);

        study.setOwner(member.get());

        Study newStudy = study;
        memberService.notify(newStudy);
        memberService.notify(member.get());

        return studyRepository.save(study);
    }

    public Study openStudy(Study study){
        study.open();
        Optional<Study> openedStudy = studyRepository.save(study);
        memberService.notify(openedStudy.get());
        return openedStudy.get();
    }
}
