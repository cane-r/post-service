package com.csiris.postservice.event;

public class PostCreatedEvent extends Event<Long>{

	private final Long postId;

	public PostCreatedEvent(Long postId) {
		this.postId = postId;
	}

	public Long getPostId() {
		return postId;
	}

	@Override
	Long getEvent() {
		return postId;
	}
}
