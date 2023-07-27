package com.depromeet.breadmapbackend.domain.admin.feed.domain;

import java.util.Objects;

import javax.persistence.*;

import com.depromeet.breadmapbackend.domain.admin.feed.dto.request.CurationFeedRequestDto;
import com.depromeet.breadmapbackend.domain.bakery.Bakery;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CurationBakery {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "curation_id")
	private CurationFeed curation;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bakery_id")
	private Bakery bakery;

	private String reason;

	private Long productId;

	public CurationBakery(CurationFeed curation, Bakery bakery, CurationFeedRequestDto requestDto) {
		this.curation = curation;
		this.bakery = bakery;
		this.reason = requestDto.getReason();
		this.productId = requestDto.getProductId();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		CurationBakery currentBakery = (CurationBakery)o;
		return Objects.equals(curation, currentBakery.getCuration())
			&& Objects.equals(bakery, currentBakery.getBakery());
	}

	@Override
	public int hashCode() {
		return Objects.hash(curation, bakery);
	}
}
