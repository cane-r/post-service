package com.csiris.postservice.endpoints;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.csiris.postservice.persistence.Post;
import com.csiris.postservice.service.PostService;
import com.csiris.postservice.transport.PostDto;
import com.csiris.postservice.util.NonNullPropertyObjectCopier;

@RestController
@RequestMapping("/api/post")
@CrossOrigin()
@Validated
public class PostRestController {
	private final PostService postService;
	private final Logger logger;

	public PostRestController(PostService postService, Logger logger) {
		super();
		this.postService = postService;
		this.logger = logger;
	}

	@GetMapping("/backdoor")
	public ResponseEntity<Iterable<Post>> getAllPosts() {
		return ResponseEntity.ok(postService.getAllPostsBackDoor());
	}

	@GetMapping
	public ResponseEntity<Iterable<PostDto>> getAllPostsDto() {
		return ResponseEntity.ok(postService.getAllPostsDto());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> getSinglePost(@PathVariable Long id) {
		return ResponseEntity.ok(postService.getSinglePost(id)
				.orElseThrow(() -> new RuntimeException(String.format("Post with %s not found on this server", id))));
	}

	// #FIXME : automatic dto-entity conversion..
	@PutMapping("/{id}")
	public ResponseEntity<Post> updateSinglePost(@PathVariable Long id,@RequestBody @Valid PostDto incoming) {
		return ResponseEntity.ok(postService.updatePost(id,incoming));
	}

	// #FIXME : automatic dto-entity conversion..
	@PostMapping
	public ResponseEntity<PostDto> savePost(@RequestBody @Valid PostDto incoming) {
		NonNullPropertyObjectCopier<PostDto, Post> c = new NonNullPropertyObjectCopier<>();
		Post post1 = new Post();
		logger.info(incoming.toString());
		c.copyProperties(incoming, post1);
		return ResponseEntity.ok(postService.savePost(post1));
	}

	// #FIXME : automatic dto-entity conversion..
	@PostMapping("/multiple")
	public ResponseEntity<Iterable<Post>> saveMultiplePost(@RequestBody List<Post> incoming) {
		NonNullPropertyObjectCopier<PostDto, Post> c = new NonNullPropertyObjectCopier<>();
		// incoming.stream().map(post -> c.copyProperties(post));
		logger.info(incoming.toString());
		return ResponseEntity.ok(postService.savaMultiplePost(incoming));
	}

	@PostMapping("/approve/{id}")
	public ResponseEntity<Boolean> approvePost(@PathVariable Long id) {
		return ResponseEntity.ok(postService.approvePost(id));
	}

	@PostMapping("/reject/{id}")
	public ResponseEntity<Boolean> rejectPost(@PathVariable Long id) {
		return ResponseEntity.ok(postService.rejectPost(id));
	}

}
