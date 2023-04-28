package com.depromeet.breadmapbackend.domain.review;

import com.depromeet.breadmapbackend.domain.bakery.Bakery;
import com.depromeet.breadmapbackend.global.BaseEntity;
import com.depromeet.breadmapbackend.domain.review.comment.ReviewComment;
import com.depromeet.breadmapbackend.domain.review.like.ReviewLike;
import com.depromeet.breadmapbackend.domain.user.User;
import com.depromeet.breadmapbackend.global.converter.BooleanToYNConverter;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
public class Review extends BaseEntity {
    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "bakery_id")
    private Bakery bakery;

    @Column(nullable = false, length = 200)
    private String content;

//    @Convert(converter = StringListConverter.class)
//    private List<String> imageList = new ArrayList<>();

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewImage> imageList = new ArrayList<>();

    @Column(nullable = false)
    @Convert(converter = BooleanToYNConverter.class)
    private Boolean isBlock = Boolean.FALSE;

    @Column(nullable = false)
    @Convert(converter = BooleanToYNConverter.class)
    private Boolean isNew = Boolean.TRUE;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true, fetch = EAGER)
    private List<ReviewProductRating> ratings = new ArrayList<>();

    @Formula("(SELECT count(*) FROM review_like rl WHERE rl.review_id = id)")
    private Integer likeNum;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewComment> comments = new ArrayList<>();

    @Builder
    private Review(User user, Bakery bakery, String content) {
        this.user = user;
        this.bakery = bakery;
        this.content = content;
        this.bakery.getReviewList().add(this);
    }

    public Double getAverageRating() {
        return Math.floor(this.ratings.stream()
                .mapToLong(ReviewProductRating::getRating).average().orElse(0)*10)/ 10.0;
    }

    public void changeBlock() {
        this.isBlock = !this.isBlock;
    }

    public void removeComment(ReviewComment reviewComment){ this.comments.remove(reviewComment); }

    public void unNew() {
        this.isNew = Boolean.FALSE;
    }
}
