package com.tonyjev93.junit5.ex.junitexample.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class) // Mock Annotation 사용 하여 Mock 생성 가능 <- 선언 안하면 @Mock = Null 됨
public class StudyServiceTest {

    // 여러 곳에서 사용 시 Annotation 활용
    @Mock
    MemberService memberService;

    @Mock
    StudyRepository studyRepository;

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
//        assertNull(optional);
        memberService.validate(2L); // void return 테스트

        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService, "Study Service is null");
    }

    @Test
    @DisplayName("Stub 생성")
    void createStubTest() {

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
    void stubOrderTest() { // Method에서만 Mock 사용 시 Parameter로 적용 가능

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
    void stubbingExercise() {

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

    @Test
    @DisplayName("Mock 객체 확인")
    void objectConfirmTest() {

        // Given
        StudyService studyService = new StudyService(memberService, studyRepository);

        Member member = new Member();
        member.setId(1L);
        member.setEmail("tonyJev93@gmail.com");

        when(memberService.findById(1L)).thenReturn(Optional.of(member));

        Study study = new Study(10, "테스트");

        // When
        studyService.createNewStudy(1L, study);

        // Then
        verify(memberService, times(1)).notify(study); // notify 함수가 n 번 수행되었는지 확인.
        verify(memberService, never()).validate(1l); // never <= 한번도 수행되지 않았는지 체크

        // 실행 순서를 체크한다.
        InOrder inOrder = inOrder(memberService);
        inOrder.verify(memberService).notify(study);
        inOrder.verify(memberService).notify(member);

        verifyNoMoreInteractions(memberService); // 더이상 함수 호출을 하지 않는지 체크

    }

    @Test
    @DisplayName("Mockito 연습 문제")
    void mockitoExercise() {

        // Given
        StudyService studyService = new StudyService(memberService, studyRepository);

        Member member = new Member();
        member.setId(1L);
        member.setEmail("tonyJev93@gmail.com");

//        when(memberService.findById(1L)).thenReturn(Optional.of(member));
        given(memberService.findById(1L)).willReturn(Optional.of(member));  // when -> given BDD API로 변형형

        Study study = new Study(10, "테스트");

        // When
        studyService.createNewStudy(1L, study);

        // Then
//        verify(memberService, times(1)).notify(study); // notify 함수가 n 번 수행되었는지 확인.
//        verify(memberService, never()).validate(1l); // never <= 한번도 수행되지 않았는지 체크
        then(memberService).should(times(1)).notify(study); // verify -> then.should
        then(memberService).should(times(1)).notify(member); // verify -> then.should
        then(memberService).should(never()).validate(1l); // verify -> then.should

//        verifyNoMoreInteractions(memberService); // 더이상 함수 호출을 하지 않는지 체크
        then(memberService).shouldHaveNoMoreInteractions(); // verifyNoMoreInteractions -> then.shouldHaveNoMoreInteractions

    }

    @Test
    @DisplayName("BDD API 사용하기 : 다른 사용자가 볼 수 있도록 스터디를 공개한다.")
    void bddApiTest() {

        // Given
        StudyService studyService = new StudyService(memberService, studyRepository);
        Study study = new Study(10, "더 자바, 테스트");
        given(studyRepository.save(study)).willReturn(Optional.of(study));

        // When
        studyService.openStudy(study);

        System.out.println(study.toString());
        // Then
        assertAll(() -> assertEquals(study.getStatus(), "OPENED", "오픈 되지 않았습니다."),
                () -> assertNotNull(study.getOpendDataTime(), "오픈 날짜가 NULL입니다."));
        then(memberService).should(times(1)).notify(study);

    }

}
