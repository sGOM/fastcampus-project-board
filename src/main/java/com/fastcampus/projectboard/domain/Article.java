package com.fastcampus.projectboard.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@ToString
@Table(indexes = {
        @Index(columnList = "title"),
        @Index(columnList = "hashtag"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy"),
})
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter @Column(nullable = false) private String title;   // 제목
    @Setter @Column(nullable = false, length = 10000) private String content; // 본문

    @Setter private String hashtag; // 해시태그

    @ToString.Exclude   // 순환 참조가 일어날 수 있음 (Article -> ArticleComment -> Article ...)
    @OrderBy("id")
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private final Set<ArticleComment> articleComments = new LinkedHashSet<>();

    // Auditing (감사)
    // CreatedBy 같은 경우 JpaConfig에서 AuditorAware로 자동 생성
    @CreatedDate @Column(nullable = false) private LocalDateTime createdAt;    // 생성 일시
    @CreatedBy @Column(nullable = false, length = 100) private String createdBy;   // 생성자
    @LastModifiedDate @Column(nullable = false) private LocalDateTime modifiedAt;   // 수정 일시
    @LastModifiedBy @Column(nullable = false, length = 100) private String modifiedBy;  // 수정자

    protected Article() {}

    // 자동생성, 메타 데이터를 제외한 생성자
    private Article(String title, String content, String hashtag) {
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }

    // 팩토리 메서드, new 키워드를 쓰지 않게
    public static Article of(String title, String content, String hashtag) {
        return new Article(title, content, hashtag);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article article)) return false;
        return id != null && id.equals(article.id);
    }
    // 아직 jpa에 영속화되지 않은 entity는 id가 부여되지 않았기 때문에 nullable임
    // 때문에 Id 속성이 nullable = false라도 체크

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
