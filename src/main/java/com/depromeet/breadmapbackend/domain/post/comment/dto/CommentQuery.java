package com.depromeet.breadmapbackend.domain.post.comment.dto;

import java.time.LocalDate;

import com.depromeet.breadmapbackend.domain.post.comment.CommentStatus;

/**
 * CommentQuery
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/07/23
 */
public record CommentQuery(
	Long commentId,
	String content,
	boolean isFirstDepth,
	Long parentId,
	Long userId,
	String nickname,
	String profileImage,
	Long likeCount,
	LocalDate createdDate,
	CommentStatus commentStatus
) {

	public CommentInfo toInfo() {
		return new CommentInfo(
			commentId,
			content,
			isFirstDepth,
			parentId,
			userId,
			nickname,
			profileImage,
			likeCount,
			createdDate
		);
	}
}
