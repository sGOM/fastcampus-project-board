package com.fastcampus.projectboard.service;

import com.fastcampus.projectboard.domain.Article;
import com.fastcampus.projectboard.domain.type.SearchType;
import com.fastcampus.projectboard.dto.ArticleDto;
import com.fastcampus.projectboard.dto.ArticleUpdateDto;
import com.fastcampus.projectboard.repository.ArticleCommentRepository;
import com.fastcampus.projectboard.repository.ArticleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

// spring boot application을 띄우지 않고 필요한 dependency는 Mocking하는 식으로 해서
// test를 가볍가 만듦

@DisplayName("비즈니스 로직 - 게시글")
// Mocking에 많이 사용되는 Lib, Mockito 사용
@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {

    // sut -> System Under Test, 테스트 짤때 많이 사용하는 네이밍
    // @InjectMocks -> Mock을 주입하는 대상, 아직 생성자 주입 불가능
    @InjectMocks private ArticleService sut;
    // @Mock -> 그 외의 나머지 모든 Mock
    @Mock private ArticleRepository articleRepository;
    @Mock private ArticleCommentRepository articleCommentRepository;

    @DisplayName("게시글을 검색하면, 게시글 리스트를 반환한다.")
    @Test
    void givenSearchParameter_whenSearchingArticles_thenReturnsArticlesList() {
        // Given

        // When
        Page<ArticleDto> articles = sut.searchArticles(SearchType.TITLE, "serach keyword"); // 제목, 본문, ID, 닉네임, 해시태그

        // Then
        assertThat(articles).isNotNull();
    }

    @DisplayName("게시글을 조회하면, 게시글을 반환한다.")
    @Test
    void givenArticleId_whenSearchingArticle_thenReturnsArticle() {
        // Given

        // When
        ArticleDto article = sut.searchArticle(1L);

        // Then
        assertThat(article).isNotNull();
    }

    @DisplayName("게시글을 정보를 입력하면, 게시글을 생성한다.")
    @Test
    void givenArticlesInfo_whenSavingArticle_thenSavesArticle() {
        // Given
        ArticleDto dto = ArticleDto.of(LocalDateTime.now(), "Gom", "title", "content", "#Spring");
        given(articleRepository.save(any(Article.class))).willReturn(null); // any : class에 맞는 아무거나 넣어줌


        // When
        sut.saveArticle(dto);

        // Then
        // ArticleRepository의 save 메서드가 호출이 되었었는가? 를 검사
        then(articleRepository).should().save(any(Article.class));
    }

    @DisplayName("게시글의 ID와 수정 정보를 입력하면, 게시글을 생성한다.")
    @Test
    void givenArticleIdAndModifiedInfo_whenUpdatingArticle_thenUpdatingArticle() {
        // Given
        ArticleUpdateDto dto = ArticleUpdateDto.of("title", "content", "#Spring");
        given(articleRepository.save(any(Article.class))).willReturn(null); // any : class에 맞는 아무거나 넣어줌


        // When
        sut.updateArticle(1L, dto);

        // Then
        // ArticleRepository의 save 메서드가 호출이 되었었는가? 를 검사
        then(articleRepository).should().save(any(Article.class));
    }

    @DisplayName("게시글의 ID를 입력하면, 게시글을 삭제한다.")
    @Test
    void givenArticleId_whenDeletingArticle_thenDeletingArticle() {
        // Given
        willDoNothing().given(articleRepository).delete(any(Article.class));

        // When
        sut.deleteArticle(1L);

        // Then
        then(articleRepository).should().delete(any(Article.class));
    }
}