package com.tonyjev93.junit5.ex.junitexample.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;


@ExtendWith(MockitoExtension.class) // Mock Annotation 사용 하여 Mock 생성 가능 <- 선언 안하면 @Mock = Null 됨
public class StudyServiceTest {

    // 여러 곳에서 사용 시 Annotation 활용
    @Mock
    MemberService memberService;


    @Test
    @DisplayName("Mock 테스트")
    void createStudyService(@Mock StudyRepository studyRepository) { // Method에서만 Mock 사용 시 Parameter로 적용 가능
//        MemberService memberService = mock(MemberService.class);
//        StudyRepository studyRepository = mock(StudyRepository.class);

        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService, "Study Service is null");
    }

}
