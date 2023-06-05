package com.depromeet.breadmapbackend.domain.notice;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import com.depromeet.breadmapbackend.domain.notice.content.AddBakeryNoticeContent;
import com.depromeet.breadmapbackend.domain.notice.content.AddProductNoticeContent;
import com.depromeet.breadmapbackend.domain.notice.content.FlagBakeryAdminNoticeNoticeContent;
import com.depromeet.breadmapbackend.domain.notice.content.FlagBakeryChangeNoticeContent;
import com.depromeet.breadmapbackend.domain.notice.content.FollowNoticeContent;
import com.depromeet.breadmapbackend.domain.notice.content.RecommentContent;
import com.depromeet.breadmapbackend.domain.notice.content.ReviewCommentLikeNoticeContent;
import com.depromeet.breadmapbackend.domain.notice.content.ReviewCommentNoticeContent;
import com.depromeet.breadmapbackend.domain.notice.content.ReviewLikeNoticeContent;
import com.depromeet.breadmapbackend.domain.notice.dto.NoticeDto;
import com.depromeet.breadmapbackend.global.dto.PageResponseDto;
import com.depromeet.breadmapbackend.global.infra.AsyncConfig;
import com.depromeet.breadmapbackend.global.infra.properties.CustomAWSS3Properties;
import com.depromeet.breadmapbackend.global.infra.properties.CustomFirebaseProperties;
import com.depromeet.breadmapbackend.utils.TestConfig;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@DataJpaTest
@Import({
	NoticeServiceImpl.class,
	NoticeQueryRepository.class,
	FcmService.class,
	NoticeContentProcessorImpl.class,
	TestConfig.class,
	AsyncConfig.class,
	AddBakeryNoticeContent.class,
	AddProductNoticeContent.class,
	FlagBakeryAdminNoticeNoticeContent.class,
	FlagBakeryChangeNoticeContent.class,
	FollowNoticeContent.class,
	RecommentContent.class,
	ReviewCommentLikeNoticeContent.class,
	ReviewCommentNoticeContent.class,
	ReviewLikeNoticeContent.class
})
@EnableConfigurationProperties({
	CustomFirebaseProperties.class,
	CustomAWSS3Properties.class
})
class NoticeServiceImplTest {

	@Autowired
	private NoticeServiceImpl sut;
	@Autowired
	private CustomAWSS3Properties customAWSS3Properties;
	@PersistenceContext
	EntityManager em;

	@Test
	@Sql("classpath:notice-test-data.sql")
	void 알림_타입에_따른_메시지와_이미지_생성_테스트() throws Exception {
		final String oAuthId = "APPLE_111";
		final NoticeDayType type = NoticeDayType.BEFORE;
		final int page = 0;

		final PageResponseDto<NoticeDto> result = sut.getNoticeList(oAuthId, type, null, page);

		assertResults(result);
	}

	@Test
	@Sql("classpath:notice-test-data.sql")
	void 알림_저장_성공_테스트() throws Exception {
		final NoticableEvent event = NoticableEvent.builder()
			.isAlarmOn(false)
			.userId(112L)
			.fromUserId(111L)
			.contentId(111L)
			.noticeType(NoticeType.FOLLOW)
			.build();

		sut.addNotice(event);
		Thread.sleep(1000L); // 비동기로 동작하기 때문에 1초 대기

		final List<Notice> notice = em.createQuery("select n from Notice n where n.userId = :userId", Notice.class)
			.setParameter("userId", 112L)
			.getResultList();
		assertThat(notice).hasSize(1);
		assertThat(notice.get(0).getType()).isEqualTo(NoticeType.FOLLOW);

	}

	private void assertResults(final PageResponseDto<NoticeDto> result) {
		assertThat(result.getContents().stream().map(NoticeDto::getTitle))
			.containsExactly(
				"관리자 글이 업데이트 됐어요!",
				"", "내가 제보한 빵이 추가되었어요!",
				"내가 제보한 빵집이 추가되었어요!",
				"내 댓글을 nick_name님이 좋아해요!",
				"내 댓글에 nick_name님이 대댓글을 달았어요!",
				"내 리뷰를 nick_name님이 좋아해요!",
				"nick_name님이 댓글을 달았어요!",
				"nick_name님이 회원님을 팔로우하기 시작했어요"
			);

		assertThat(result.getContents().stream().map(NoticeDto::getImage))
			.containsExactly(
				"%s/%s.png".formatted(customAWSS3Properties.getCloudFront(),
					customAWSS3Properties.getDefaultImage().getFlag()),
				"%s/%s.png".formatted(customAWSS3Properties.getCloudFront(),
					customAWSS3Properties.getDefaultImage().getFlag()),
				"%s/%s.png".formatted(customAWSS3Properties.getCloudFront(),
					customAWSS3Properties.getDefaultImage().getReport()),
				"%s/%s.png".formatted(customAWSS3Properties.getCloudFront(),
					customAWSS3Properties.getDefaultImage().getReport()),
				"%s/%s.png".formatted(customAWSS3Properties.getCloudFront(),
					customAWSS3Properties.getDefaultImage().getLike()),
				"%s/%s.png".formatted(customAWSS3Properties.getCloudFront(),
					customAWSS3Properties.getDefaultImage().getComment()),
				"%s/%s.png".formatted(customAWSS3Properties.getCloudFront(),
					customAWSS3Properties.getDefaultImage().getLike()),
				"%s/%s.png".formatted(customAWSS3Properties.getCloudFront(),
					customAWSS3Properties.getDefaultImage().getComment()),
				"image"
			);
	}
}