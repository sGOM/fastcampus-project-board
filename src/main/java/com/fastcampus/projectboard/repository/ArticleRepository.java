package com.fastcampus.projectboard.repository;

import com.fastcampus.projectboard.domain.Article;
import com.fastcampus.projectboard.domain.QArticle;
import com.fastcampus.projectboard.repository.querydsl.ArticleRepositoryCustom;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ArticleRepository extends
        JpaRepository<Article, Long>,
        ArticleRepositoryCustom,
        QuerydslPredicateExecutor<Article>, // Article(entity) 안에 있는 기본검색 기능 추가
        QuerydslBinderCustomizer<QArticle> {

    // Containing -> exact 매칭이 아닌 부분 매칭
    Page<Article> findByTitleContaining(String title, Pageable pageable);
    Page<Article> findByContentContaining(String content, Pageable pageable);
    Page<Article> findByUserAccount_UserIdContaining(String userId, Pageable pageable);
    Page<Article> findByUserAccount_NicknameContaining(String nickname, Pageable pageable);
    Page<Article> findByHashtag(String hashtag, Pageable pageable);

    @Override
    default void customize(QuerydslBindings bindings, QArticle root) {
        // listing을 하지 않은 property는 검색에서 제외
        bindings.excludeUnlistedProperties(true);
        // 검색이 되길 원하는 property 추가 (content는 너무 크기가 커서 생각해볼 필요 있음)
        // id는 인증 기능 구현 후에
        bindings.including(root.title, root.content, root.hashtag, root.createdAt, root.createdBy);
        // first() -> 검색 parameter를 하나 만 받음
        // likeIgnoreCase == like '%${v}'
        // containsIgnoreCase == like '%${v}%'
        bindings.bind(root.title).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.content).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.hashtag).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.createdAt).first(DateTimeExpression::eq); // 크거나 같거나
        bindings.bind(root.createdBy).first(StringExpression::containsIgnoreCase);
    }
}
