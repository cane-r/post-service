package com.csiris.postservice.persistence.listener

import org.springframework.stereotype.Component;

import com.csiris.postservice.persistence.Post;

import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDateTime

@Component
class PostAuditListener //constructor (private val logger:Logger)
{

	private val logger = LoggerFactory.getLogger(PostAuditListener::class.java)

	//@PrePersist
	@PreUpdate
	//@PreRemove
	fun beforeUpdate(post: Post) {
		logger.info(String.format("%s about to be updated", post));
		post.updatedAt = LocalDateTime.now();
	}

	@PostPersist
	@PostUpdate
	@PostRemove
	//this can be used to emit domain events also..insted of spring application events..
	fun afterUpdate(post: Post) {
		logger.info(String.format("%s has been added/updated/deleted", post));
	}

	@PostLoad
	fun afterLoad(post: Post) {
		logger.info(String.format("%s has been loaded from the DB", post));

	}
}