package com.github.cidarosa.algaposts.post.service.domain.service;

import com.github.cidarosa.algaposts.post.service.api.model.*;
import com.github.cidarosa.algaposts.post.service.domain.model.Post;
import com.github.cidarosa.algaposts.post.service.domain.repository.PostRepository;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;
import java.util.stream.Collectors;

import static com.github.cidarosa.algaposts.post.service.infrastructure.rabbitmq.RabbitConfig.POST_QUEUE;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final AmqpTemplate amqpTemplate;

    public PostService(PostRepository postRepository, AmqpTemplate amqpTemplate) {
        this.postRepository = postRepository;
        this.amqpTemplate = amqpTemplate;
    }

    @Transactional
    public PostOutput save (PostInput input){

        Post post = Post.builder()
                .id(UUID.randomUUID())
                .title(input.getTitle())
                .body(input.getBody())
                .author(input.getAuthor())
                .wordCount(null)
                .calculatedValue(null)
                .build();
        post = postRepository.saveAndFlush(post);

        amqpTemplate.convertAndSend(POST_QUEUE, new PostProcessingRequest(post.getId(), input.getBody()));

        return convertToModel(post);
    }

    @Transactional
    public void updatePostInfo(PostProcessingResult result) {

        Post post = postRepository.findById(result.getPostId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        post.setWordCount(result.getWordCount());
        post.setCalculatedValue(result.getCalculatedValue());
        postRepository.save(post);
    }

    @Transactional(readOnly = true)
    public PostOutput findById(UUID id){
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );

        return convertToModel(post);
    }

    @Transactional(readOnly = true)
    public Page<PostSummaryOutput> findAll(Pageable pageable){

        Page<Post> posts = postRepository.findAll(pageable);

        return posts.map(this::convertSummaryToModel);
    }

    private PostOutput convertToModel(Post post) {

        return PostOutput.builder()
                .id(post.getId())
                .title(post.getTitle())
                .body(post.getBody())
                .author(post.getAuthor())
                .wordCount(post.getWordCount())
                .calculatedValue(post.getCalculatedValue())
                .build();
    }

    private PostSummaryOutput convertSummaryToModel(Post post) {

        return PostSummaryOutput.builder()
                .id(post.getId())
                .title(post.getTitle())
                .author(post.getAuthor())
                .summary(post.getBody().lines().limit(3).collect(Collectors.joining("\n")))
                .build();
    }


}
