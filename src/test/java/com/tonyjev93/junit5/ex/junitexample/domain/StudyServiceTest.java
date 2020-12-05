package com.tonyjev93.junit5.ex.junitexample.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class) // Mock Annotation 사용 하여 Mock 생성 가능 <- 선언 안하면 @Mock = Null 됨
public class StudyServiceTest {

    // 여러 곳에서 사용 시 Annotation 활용
    @Mock
    MemberService memberService;


    @Test
    @DisplayName("Mock 서비스")
    void createStudyService(@Mock StudyRepository studyRepository) { // Method에서만 Mock 사용 시 Parameter로 적용 가능
//        MemberService memberService = mock(MemberService.class);
//        StudyRepository studyRepository = mock(StudyRepository.class);
        Member member = new Member();
        member.setId(1L);
        member.setEmail("tonyJev93@gmail.com");

        // 1을 호출하면 member를 반환
        when(memberService.findById(1L)).thenReturn(Optional.of(member));

        Optional<Member> optional = memberService.findById(1L); // optional 객체 return 테스트
        assertNull(optional);
        memberService.validate(2L); // void return 테스트

        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService, "Study Service is null");
    }

    @Test
    @DisplayName("Stub 생성")
    void createStubTest(@Mock StudyRepository studyRepository) { // Method에서만 Mock 사용 시 Parameter로 적용 가능

        Member member = new Member();
        member.setId(1L);
        member.setEmail("tonyJev93@gmail.com");

        // findById가 1을 호출 하면 위에 설정한 member 를 반환
//        when(memberService.findById(1L)).thenReturn(Optional.of(member));
        when(memberService.findById(any())).thenReturn(Optional.of(member));    // Argument Matcher : any() <= 아무 거나 호출 해도 됨
//        when(memberService.findById(1L)).thenThrow(new RuntimeException());  // 예외 Throw 가능


        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService, "Study Service is null");

        Study study = new Study(10, "java");

        Optional<Member> findById = memberService.findById(2L);
        assertEquals("tonyJev93@gmail.com", findById.get().getEmail());

        doThrow(new IllegalAccessError()).when(memberService).validate(1L); // void의 Throw는 doThrow로 확인

        // assertThrows = 예외처리 테스트
        assertThrows(IllegalAccessError.class, () -> {
            memberService.validate(1L);
        });

        memberService.validate(2L);

//        studyService.createNewStudy(1L, study);
    }

    @Test
    @DisplayName("stub 호출 순서")
    void stubOrderTest(@Mock StudyRepository studyRepository) { // Method에서만 Mock 사용 시 Parameter로 적용 가능

        Member member = new Member();
        member.setId(1L);
        member.setEmail("tonyJev93@gmail.com");

        when(memberService.findById(any())).thenReturn(Optional.of(member))
                .thenThrow(new RuntimeException())
                .thenReturn(Optional.empty());

        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService, "Study Service is null");

        Study study = new Study(10, "java");

        Optional<Member> findById = memberService.findById(1L);
        assertEquals("tonyJev93@gmail.com", findById.get().getEmail());

        assertThrows(RuntimeException.class, () -> {
            memberService.findById(2L);
        });

        assertEquals(Optional.empty(), memberService.findById(3L));

    }

    @Test
    @DisplayName("Mock Stubbing 연습문제")
    void excersise(@Mock StudyRepository studyRepository) {

        StudyService studyService = new StudyService(memberService, studyRepository);

        Member member = new Member();
        member.setId(1L);
        member.setEmail("tonyJev93@gmail.com");

        Study study = new Study(10, "테스트");

        when(memberService.findById(1L)).thenReturn(Optional.of(member));
        when(studyRepository.save(study)).thenReturn(Optional.of(study));

        studyService.createNewStudy(1L, study);

        assertNotNull(study.getOwner());
        assertEquals(member, study.getOwner());
    }

}
