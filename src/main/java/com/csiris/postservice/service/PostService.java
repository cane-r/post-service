package com.csiris.postservice.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.csiris.postservice.event.PostCreatedEvent;
import com.csiris.postservice.persistence.Post;
import com.csiris.postservice.persistence.storage.PostStorage;
import com.csiris.postservice.transport.PostDto;
import com.csiris.postservice.util.NonNullPropertyObjectCopier;

@Service
@Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ)
public class PostService {

	private final PostStorage repository;
	private final ApplicationEventPublisher eventPublisher;

	public PostService(PostStorage repository, ApplicationEventPublisher eventPublisher) {
		super();
		this.repository = repository;
		this.eventPublisher = eventPublisher;
	}

	/**
	 * Method for returning all posts that have been moderated,used by public
	 * controller method
	 * 
	 * @param <Post> {@link com.csiris.postservice.persistence.Post}
	 * @return Iterable
	 */

	public Iterable<Post> getAllPostsBackDoor() {
		return repository.findAll();
	}

	public Iterable<Post> getAllPosts() {
//		repository.findAll().forEach((post)-> 
//		{
		// FIXME Later use annotation for time measurement and transaction telemetry
		// @Time(measureIn=MeasurementUnit.MS)
//			System.out.println(post.getCreatedAt() + " : " + post.getUpdatedAt()); 
//			
//			try {
//				Thread.sleep(1000);
//				post.setApproved(true);
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			repository.save(post);
//			});
		// return repository.findAll();
		// return List.of(repository.findByApprovedTrue());
		return repository.findByApprovedTrue();
	}

	public List<PostDto> getAllPostsDto() {
		NonNullPropertyObjectCopier<Post, PostDto> c = new NonNullPropertyObjectCopier<>();

		return repository.findByApprovedTrue().stream().map(post -> {
			PostDto dto = new PostDto();
			c.copyProperties(post, dto);
			return dto;
		}).collect(Collectors.toList());
	}

	@Transactional(rollbackFor = Exception.class)
	// #FIXME : automatic dto-entity conversion..
	public PostDto savePost(Post post) {
		Post savedPost = repository.save(post);
		NonNullPropertyObjectCopier<Post, PostDto> c = new NonNullPropertyObjectCopier<>();
		PostDto dto = new PostDto();
		c.copyProperties(post, dto);
		// fire an domain event after this transaction completes its job..
		eventPublisher.publishEvent(new PostCreatedEvent(savedPost.getId()));
		return dto;
	}

	public Optional<Post> getSinglePost(Long id) {
		return repository.findByIdAndApprovedTrue(id);
	}

	@Transactional(rollbackFor = Exception.class)
	public boolean approvePost(Long id) {
		// repository.approvePost(id);
		Post post = repository.findById(id).orElseThrow(() -> new RuntimeException("oooops"));
		// eventPublisher.publishEvent(new PostApprovedEvent(id));
		post.setApproved(true);
		return true;
	}

	@Transactional(rollbackFor = Exception.class)
	public boolean rejectPost(Long id) {
		// repository.rejectPost(id);
		Post post = repository.findById(id).orElseThrow(() -> new RuntimeException("oooops"));
		post.setApproved(false);
		// eventPublisher.publishEvent(new PostRejectedEvent(id));
		return true;
	}

	/**
	 * Saves multiple posts convenience method.
	 * 
	 * @param <Post>   {@link com.csiris.postservice.persistence.Post}
	 * @param incoming json array/xml collection payload
	 * @see Iterable
	 */
	@Transactional(rollbackFor = Exception.class)
	public Iterable<Post> savaMultiplePost(List<Post> incoming) {
		return repository.saveAll(incoming);
	}

	@Transactional(rollbackFor = Exception.class)
	public Post updatePost(Long id, @Valid PostDto incoming) {
		// repository.rejectPost(id);
		Post post = repository.findById(id).orElseThrow(() -> new RuntimeException("oooops"));
		//NonNullPropertyObjectCopier<PostDto,Post > c = new NonNullPropertyObjectCopier<>();
		//c.copyProperties(incoming, post);
		post.setTitle(incoming.getTitle());
		post.setContent(incoming.getContent());
		repository.save(post);
		// eventPublisher.publishEvent(new PostUpdatedEvent(id));
		return post;
	}

}
