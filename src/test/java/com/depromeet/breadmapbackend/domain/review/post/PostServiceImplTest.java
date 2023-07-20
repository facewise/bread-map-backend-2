package com.depromeet.breadmapbackend.domain.review.post;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.depromeet.breadmapbackend.domain.review.post.dto.PostDetailInfo;
import com.depromeet.breadmapbackend.domain.review.post.dto.PostRegisterCommand;
import com.depromeet.breadmapbackend.domain.review.post.image.PostImage;

/**
 * PostServiceImplTest
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/07/20
 */
class PostServiceImplTest extends PostServiceTest {

	@Autowired
	private PostServiceImpl sut;

	@Autowired
	private EntityManager em;

	@Test
	@Sql("classpath:post-test-data.sql")
	void 빵이야기_등록() throws Exception {
		//given
		final Long userId = 111L;
		final String title = "title test";
		final String content = "content test";
		final PostTopic topic = PostTopic.BREAD_STORY;
		final List<String> images = List.of("image1", "image2");
		final PostRegisterCommand command = new PostRegisterCommand(title, content, images, topic);

		//when
		sut.register(command, userId);
		//then
		final Post savedPost = em.createQuery("select p from Post p  ", Post.class)

			.getSingleResult();

		assertThat(savedPost.getTitle()).isEqualTo(title);
		assertThat(savedPost.getContent()).isEqualTo(content);
		assertThat(savedPost.getPostTopic()).isEqualTo(topic);
		assertThat(savedPost.getImages()).hasSize(2);
		assertThat(savedPost.getImages().stream().map(PostImage::getImage).toList())
			.containsExactly("image1", "image2");
		assertThat(savedPost.getUser().getNickName()).isEqualTo("nick_name");
	}

	@Test
	@Sql("classpath:post-test-data.sql")
	void 빵이야기_상세_조회() throws Exception {
		//given
		final Long userId = 111L;
		final Long postId = 111L;

		//when
		final PostDetailInfo result = sut.getPost(postId, userId);

		//then
		assertThat(result.writerInfo()).isNotNull();
		assertThat(result.writerInfo().followerCount()).isEqualTo(2);
		assertThat(result.writerInfo().reviewCount()).isEqualTo(3);
		assertThat(result.writerInfo().isFollowed()).isTrue();
		assertThat(result.commentCount()).isEqualTo(2);
		assertThat(result.title()).isEqualTo("test-title");
		assertThat(result.content()).isEqualTo("test-content");
		assertThat(result.createdDate()).isEqualTo("2021-07-20");
		assertThat(result.images()).hasSize(2);

	}

}