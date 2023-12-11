package com.example.datatesting.service;

import com.example.datatesting.entity.Post;
import com.example.datatesting.entity.Tag;
import com.example.datatesting.repository.PostRepository;
import com.example.datatesting.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ManyToManyRelationshipService {
    // use set instead of list
    // choose relationship owner
    // cascade only from owner side
    // do NOT cascade deletes
    // specify join table
    // specify lazy fetching on both sides
    // keep both sides in sync
    // equals, hash code, to string overriding
    // specify join column

    private final PostRepository postRepository;
    private final TagRepository tagRepository;

    private static final Long POST_ID = 1L;
    private static final Long TAG_ID = 1L;
    private static final String TAG_NAME = "t1 name";

    @Transactional
    public void addPostsAndTags() {
        Post post1 = new Post();
        post1.setName("p1 name");
        Post post2 = new Post();
        post2.setName("p2 name");

        Tag tag1 = new Tag();
        tag1.setName(TAG_NAME);
        Tag tag2 = new Tag();
        tag2.setName("t2 name");
        Tag tag3 = new Tag();
        tag3.setName("t3 name");

        post1.addTag(tag1);
        post1.addTag(tag2);
        post1.addTag(tag3);

        post2.addTag(tag1);

        postRepository.save(post1);
        postRepository.save(post2);
    }

    // TODO Optimize fetching
    @Transactional
    public void removeTagFromFirstPost() {
        Post post = postRepository.findById(POST_ID)
            .orElseThrow(() -> new RuntimeException("Demonstration error"));

        Tag tag = post.getTags().stream()
            .filter(t -> t.getName().equalsIgnoreCase(TAG_NAME))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Demonstration error"));

        post.removeTag(tag);
    }

    @Transactional
    public void removePost() {
        Post post = postRepository.findById(POST_ID)
            .orElseThrow(() -> new RuntimeException("Demonstration error"));

        postRepository.delete(post);
    }

    @Transactional
    public void removeTag() {
        Tag tag = tagRepository.findById(TAG_ID)
            .orElseThrow(() -> new RuntimeException("Demonstration error"));

        tag.getPosts()
            .forEach(post -> post.removeTag(tag));

        tagRepository.delete(tag);
    }
}
