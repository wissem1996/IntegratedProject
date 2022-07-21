package com.esprit.voyage.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.esprit.voyage.entity.Like;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

	@Transactional
	void deleteByUserIdAndPostId(int userId, int postId);

	Boolean existsByUserIdAndPostId(int userId, int postId);

	Like findByUserIdAndPostId(int userId, int postId);

	@Transactional
	void deleteByUserIdAndCommentId(int userId, int commentId);

	Boolean existsByUserIdAndCommentId(int userId, int commentId);

	Like findByUserIdAndCommentId(int userId, int commentId);

}
