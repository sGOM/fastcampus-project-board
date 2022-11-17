package com.fastcampus.projectboard.controller;

import com.fastcampus.projectboard.dto.UserAccountDto;
import com.fastcampus.projectboard.dto.request.ArticleCommentRequest;
import com.fastcampus.projectboard.service.ArticleCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

// GET, POST 밖에 없는 이유는 <form> 태그가 GET, POST 밖에 못하기 때문, 그것이 HTTP 표준임
@RequiredArgsConstructor
@RequestMapping("/comments")
@Controller
public class ArticleCommentController {

    private final ArticleCommentService articleCommentService;

    @PostMapping("/new")
    public String postNewArticleComment(ArticleCommentRequest articleCommentRequest) {
        // TODO: 인증 정보를 넣어줘야 함
        articleCommentService.saveArticleComment(articleCommentRequest.toDto(UserAccountDto.of(
                "gom", "134679sd", "gom@mail.com", "Gom", "I am Gom."
        )));

        return "redirect:/articles/" + articleCommentRequest.articleId();
    }

    @PostMapping("/{commentId}/delete")
    public String deleteArticleComment(@PathVariable Long commentId, Long articleId) {
        articleCommentService.deleteArticleComment(commentId);

        return "redirect:/articles/" + articleId;
    }
}
