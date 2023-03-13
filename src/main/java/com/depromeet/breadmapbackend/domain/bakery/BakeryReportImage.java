package com.depromeet.breadmapbackend.domain.bakery;

import com.depromeet.breadmapbackend.domain.common.BaseEntity;
import com.depromeet.breadmapbackend.domain.common.converter.BooleanToYNConverter;
import com.depromeet.breadmapbackend.domain.user.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BakeryReportImage extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bakery_id")
    private Bakery bakery;

    @Column(nullable = false)
    private String image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    @Convert(converter = BooleanToYNConverter.class)
    private Boolean isNew;

    @Builder
    public BakeryReportImage(Bakery bakery, String image, User user) {
        this.bakery = bakery;
        this.image = image;
        this.user = user;
        this.isNew = true;
    }

    public void unNew() {
        this.isNew = false;
    }
}
