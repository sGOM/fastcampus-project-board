package com.fastcampus.projectboard.controller;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Disabled("Spring Data REST 통합테스트는 불필요하므로 제외시킴")
@DisplayName("Data REST - API 테스트")
// Test에서 실행하는 transaction은 기본적으로 rollback이 됨
@Transactional
// Mock MVC의 존재를 알 수가 없으므로 애너테이션 추가
@AutoConfigureMockMvc
/**
 * Web MVC test는 slice test임
 * (controller 이외 bean들을 load하지 않음, 또 연관 되어 있다고 해도 최소한으로 읽어들임)
 * 여기선 data rest의 auto configuration을 읽지 않음
 * 그래서 @SpringBootTest로 그냥 Integration Test로 작성
 */
@SpringBootTest
public class DataRestTest {

    // Controller에 대한 test (Web MVC test)
    // 내부적으로 McokMvc를 주입 받을 수 있음
    private final MockMvc mvc;

    public DataRestTest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }

    @DisplayName("[api] 게시글 리스트 조회")
    @Test
    void givenNothing_whenRequestingArticles_thenReturnArticlesJsonResponse() throws Exception {
        // Given

        // When & Then
        mvc.perform(get("/api/articles"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("application/hal+json")));
    }

    @DisplayName("[api] 게시글 단건 조회")
    @Test
    void givenNothing_whenRequestingArticle_thenReturnArticleJsonResponse() throws Exception {
        // Given

        // When & Then
        mvc.perform(get("/api/articles/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("application/hal+json")));
    }

    @DisplayName("[api] 게시글 => 댓글 리스트 조회")
    @Test
    void givenNothing_whenRequestingArticleComments_thenReturnArticleCommentsJsonResponse() throws Exception {
        // Given

        // When & Then
        mvc.perform(get("/api/articles/1/articleComments"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("application/hal+json")));
    }

    @DisplayName("[api] 댓글 단건 조회")
    @Test
    void givenNothing_whenRequestingArticleComment_thenReturnArticleCommentJsonResponse() throws Exception {
        // Given

        // When & Then
        mvc.perform(get("/api/articleComments/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("application/hal+json")));
    }

    @DisplayName("[api] 회원 관련 API 는 일체 제공하지 않는다.")
    @Test
    void givenNothing_whenRequestingUserAccounts_thenThrowsException() throws Exception {
        // Given

        // When & Then

        mvc.perform(get("/api/userAccounts")).andExpect(status().isNotFound());

        mvc.perform(post("/api/userAccounts")).andExpect(status().isNotFound());

        mvc.perform(put("/api/userAccounts")).andExpect(status().isNotFound());

        mvc.perform(patch("/api/userAccounts")).andExpect(status().isNotFound());

        mvc.perform(delete("/api/userAccounts")).andExpect(status().isNotFound());

        mvc.perform(head("/api/userAccounts")).andExpect(status().isNotFound());
    }
}
