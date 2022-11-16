package com.fastcampus.projectboard.repository.querydsl;

import com.fastcampus.projectboard.domain.Article;
import com.fastcampus.projectboard.domain.QArticle;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

// 사용하려면 ArticleRepository 가서 ArticleRepositoryCustom 상속받게해야됨
public class ArticleRepositoryCustomImpl extends QuerydslRepositorySupport implements ArticleRepositoryCustom {

    public ArticleRepositoryCustomImpl() {
        super(Article.class);
    }

    @Override
    public List<String> findAllDistinctHashtags() {
        // Querydsl이 자동생성한 Q Object
        QArticle article = QArticle.article;

        // Generic 생략하지 않는 것이 바람직
        //JPQLQuery<String> query =
        return from(article)
                .distinct()
                .select(article.hashtag)// 기본은 article로 도메인 다 잡기
                .where(article.hashtag.isNotNull()) // null 제외
                .fetch();
    }
}
