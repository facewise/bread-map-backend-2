package com.depromeet.breadmapbackend.domain.admin.post.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.depromeet.breadmapbackend.domain.admin.post.domain.PostManagerMapper;

/**
 * PostAdminRepository
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/07/24
 */
public interface PostAdminRepository {
	Page<PostManagerMapper> findPostManagerMappers(int page);

	PostManagerMapper savePostManagerMapper(PostManagerMapper postManagerMapper);

	boolean canFixEvent();

	Optional<PostManagerMapper> findFixedPost();

	List<PostManagerMapper> findCarouselPosts();
}
