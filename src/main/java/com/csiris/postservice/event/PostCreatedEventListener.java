package com.csiris.postservice.event;

import org.slf4j.Logger;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.csiris.postservice.event.contract.InternalEventListener;

/**
 * Listens for Spring related application event of type PostCreatedEvent
 * 
 * 
 * Type @param <PostCreatedEvent> Event type
 * 
 */
@Component
public class PostCreatedEventListener implements InternalEventListener<PostCreatedEvent> {

	private final Logger logger;

	public PostCreatedEventListener(Logger logger) {
		super();
		this.logger = logger;
	}

	// @EventListener(condition = "#postCreatedEvent.id ne 0")
	@EventListener(PostCreatedEvent.class)
	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Override
	public void onEvent(PostCreatedEvent postCreated) {
		logger.info(String.format("Post with id %s has just been created", postCreated.getPostId()));
		// send to kafka..
	}
}
