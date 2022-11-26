package com.fastcampus.projectboard.repository;

import com.fastcampus.projectboard.domain.ArticleComment;
import com.fastcampus.projectboard.domain.QArticleComment;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface ArticleCommentRepository extends
        JpaRepository<ArticleComment, Long>,
        QuerydslPredicateExecutor<ArticleComment>, // ArticleComment(entity) 안에 있는 기본검색 기능 추가
        QuerydslBinderCustomizer<QArticleComment> {

    // 연관관계에 연결되어있는 article의 Id를 조회하겠다는 뜻 (id가 아닌 다른 것도 됨)
    List<ArticleComment> findByArticle_Id(Long articleId);
    void deleteByIdAndUserAccount_UserId(Long articleCommentId, String userId);

    @Override
    default void customize(QuerydslBindings bindings, QArticleComment root) {
        // listing을 하지 않은 property는 검색에서 제외
        bindings.excludeUnlistedProperties(true);
        // 검색이 되길 원하는 property 추가 (content는 너무 크기가 커서 생각해볼 필요 있음)
        // id는 인증 기능 구현 후에
        bindings.including(root.content, root.createdAt, root.createdBy);
        // first() -> 검색 parameter를 하나 만 받음
        // likeIgnoreCase == like '%${v}'
        // containsIgnoreCase == like '%${v}%'
        bindings.bind(root.content).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.createdAt).first(DateTimeExpression::eq); // 크거나 같거나
        bindings.bind(root.createdBy).first(StringExpression::containsIgnoreCase);
    }
}
