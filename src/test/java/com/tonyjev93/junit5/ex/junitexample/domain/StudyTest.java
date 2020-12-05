package com.tonyjev93.junit5.ex.junitexample.domain;

import org.junit.jupiter.api.*;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class) // 테스트 명칭 규칙
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // Class 당 Instance 하나씩 생성 하도록 설정(형태 유지)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("JUnit5 학습")
// 테스트 실행순서
class StudyTest {
    public static final String testDvision = "=============";
    int order = 1;
    Study study = new Study();


    @BeforeAll
    static void beforeAll() {
        System.out.println(testDvision + " Before All : 테스트 시작 " + testDvision);
    }

    @AfterAll
    static void afterAll() {
        System.out.println(testDvision + " After All : 테스트 종료 " + testDvision);
    }

    @BeforeEach
    void beforeEach() {
        System.out.println(testDvision + " Before Each : " + order + " 번째 테스트 시작 " + testDvision);
    }

    @AfterEach
    void afterEach() {
        System.out.println(testDvision + "  After Each : " + order++ + " 번째 테스트 종료  " + testDvision);

    }

    @Test
    @DisplayName("Assert 총집합")
    @Order(1)
    void assertTest() {
        Boolean passYn = true;

        if (passYn) {
            //true
            assertAll(
                    () -> assertEquals("B", "B", "값이 다릅니다."),
                    () -> assertNotNull("", "값이 Null이면 안됩니다."),
                    () -> assertTrue(true, "True가 아닙니다."),
                    () -> assertThrows(AssertionError.class, () -> assertTrue(false)),
                    () -> assertTimeout(Duration.ofMillis(1000), () -> Thread.sleep(500), "타임아웃 발생")
            );
        } else {
            // false
            assertAll(
                    () -> assertEquals("A", "B", "값이 다릅니다."),
                    () -> assertNotNull(null, "값이 Null이면 안됩니다."),
                    () -> assertTrue(false, "True가 아닙니다."),
                    () -> assertThrows(RuntimeException.class, () -> assertTrue(false)),
                    () -> assertTimeout(Duration.ofMillis(1000), () -> Thread.sleep(1005), "타임아웃 발생")
            );
        }
    }

    @Test
    @DisplayName("Disabled Test")
    @Disabled
    void disabledTest() {
        System.out.println("Disabled 됬음");
    }

    @Test
    @Order(2)
    @DisplayName("Order 테스트")
    void orderTest() {
        System.out.println("테스트 순서 테스트 : 2번");
    }

    @Test
    @DisplayName("Assume 테스트")
    void assumeTest() {
        assumeTrue(true);

        System.out.println("assume 통과");
    }


}
