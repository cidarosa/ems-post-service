package com.github.cidarosa.algaposts.post.service.api.controller;

import com.github.cidarosa.algaposts.post.service.api.model.PostInput;
import com.github.cidarosa.algaposts.post.service.api.model.PostOutput;
import com.github.cidarosa.algaposts.post.service.api.model.PostSummaryOutput;
import com.github.cidarosa.algaposts.post.service.domain.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

     private final PostService postService;

     @GetMapping
     public ResponseEntity<Page<PostSummaryOutput>> getAll(@PageableDefault Pageable pageable){

        return ResponseEntity.ok(postService.findAll(pageable));

     }

     @GetMapping("/{postId}")
     public ResponseEntity<PostOutput> getOne(@PathVariable UUID postId){

         return ResponseEntity.ok(postService.findById(postId));
     }

    @PostMapping
    public ResponseEntity<PostOutput> create(@RequestBody @Valid PostInput input){

        PostOutput output = postService.save(input);

        return ResponseEntity.status(HttpStatus.CREATED).body(output);

    }


}
