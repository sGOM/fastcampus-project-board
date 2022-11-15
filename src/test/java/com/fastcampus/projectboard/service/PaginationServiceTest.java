package com.fastcampus.projectboard.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@DisplayName("비즈니스 로직 - 페이지네이션")
// 옵션 설명
// webEnvironment -> Mock 이면 Mocking 한 값, PORT 가 붙은건 실제 웹환경, NONE 이면 웹환경을 전혀 넣지 않음
// classes -> 설정 클래스 지정, 기본값으로 root application 에서 시작하는 모든 Bean Scan 대상들을 Configuration 으로 불러들이는 것
//              이를 임의로 지정해주거나 Void.class 로 아무것도 못 읽게 할 수 있음
// 이렇게 하면 많이 가벼워짐 (물론 SpringBootTest 를 안쓰는게 제일 가벼움)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = PaginationService.class)
class PaginationServiceTest {

    private final PaginationService sut;

    public PaginationServiceTest(@Autowired PaginationService paginationService) {
        this.sut = paginationService;
    }

    @DisplayName("현재 페이지 번호와 총 페이지 수를 주면, 페이징 바 리스트를 만들어준다.")
    @MethodSource   // 메서드 주입 방법
    // 각각 Unit test 제목의 포맷을 정해줌
    @ParameterizedTest(name = "[{index}] 현재 페이지 {0}, 총 페이지 {1} => {2}")
    void givenCurrentPageNumberAndTotalPages_whenCalculating_thenReturnsPaginationBarNumbers(int currentPageNumber, int totalPages, List<Integer> expected) {
        // Given

        // When
        List<Integer> actual =  sut.getPaginationBarNumbers(currentPageNumber, totalPages);

        // Then
        assertThat(actual).isEqualTo(expected);
    }

    // MethodSource
    static Stream<Arguments> givenCurrentPageNumberAndTotalPages_whenCalculating_thenReturnsPaginationBarNumbers() {
        return Stream.of(
                arguments(0, 13, List.of(0, 1, 2, 3, 4)),
                arguments(1, 13, List.of(0, 1, 2, 3, 4)),
                arguments(2, 13, List.of(0, 1, 2, 3, 4)),
                arguments(3, 13, List.of(1, 2, 3, 4, 5)),
                arguments(4, 13, List.of(2, 3, 4, 5, 6)),
                arguments(5, 13, List.of(3, 4, 5, 6 ,7)),
                arguments(6, 13, List.of(4, 5, 6, 7, 8)),
                arguments(7, 13, List.of(5, 6, 7, 8, 9)),
                arguments(8, 13, List.of(6, 7, 8, 9 ,10)),
                arguments(9, 13, List.of(7, 8, 9, 10, 11)),
                arguments(10, 13, List.of(8, 9, 10, 11, 12)),
                arguments(11, 13, List.of(9, 10, 11, 12)),
                arguments(12, 13, List.of(10, 11, 12))
        );
    }

    // Spec 을 나타내기 위해 작성한 코드
    @DisplayName("현재 설정되어 있는 페이지네이션 바의 길이를 알려준다.")
    @Test
    void givenNothing_whenCalling_thenReturnsCurrentBarLength() {
        // Given

        // When
        int barLength = sut.currentBarLength();

        // Then
        assertThat(barLength).isEqualTo(5);
    }
}
