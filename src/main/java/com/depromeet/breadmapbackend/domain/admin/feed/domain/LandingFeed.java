package com.depromeet.breadmapbackend.domain.admin.feed.domain;

import java.time.LocalDateTime;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.depromeet.breadmapbackend.domain.admin.Admin;
import com.depromeet.breadmapbackend.domain.admin.category.domain.Category;
import com.depromeet.breadmapbackend.domain.admin.feed.dto.request.FeedRequestDto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "LANDING_FEED")
@Getter
@DiscriminatorValue(value = "LANDING")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LandingFeed extends Feed {

	private String redirectUrl;

	@Builder
	public LandingFeed(Admin admin, Category category,
		String subTitle, String introduction, String conclusion, String thumbnailUrl, String feedType,
		LocalDateTime activeTime, FeedStatus activated, String redirectUrl) {
		super(admin, category, subTitle, introduction, conclusion, thumbnailUrl, feedType, activeTime, activated);
		this.redirectUrl = redirectUrl;
	}

	public void update(Category category, FeedRequestDto updateDto) {
		super.update(category, updateDto.getCommon());

		this.redirectUrl = updateDto.getLanding().getRedirectUrl();
	}
}
