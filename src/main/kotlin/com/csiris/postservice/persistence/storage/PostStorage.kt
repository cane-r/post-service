package com.csiris.postservice.persistence.storage

import com.csiris.postservice.transport.PostDto;

import java.util.Optional;
import com.csiris.postservice.persistence.Post;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

interface PostStorage : CrudRepository<Post, Long> {

	//dont need AndProcessedTrue
	fun findByApprovedTrue(): List<Post>;

	//for kafka processing..
	fun findByProcessedFalse(): List<Post>;

	fun findByIdAndApprovedTrue(id: Long): Optional<Post>;

//	@Modifying(flushAutomatically = true, clearAutomatically = true)
//	@Query("update Post post set post.approved = TRUE where post.id =:id")
//	fun approvePost(id : Long);
//
//	@Modifying(flushAutomatically = true, clearAutomatically = true)
//	@Query("update Post post set post.approved = FALSE where post.id =:id")
//	fun rejectPost(id : Long);
}